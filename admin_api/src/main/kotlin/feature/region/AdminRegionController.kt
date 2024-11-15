package com.moineaufactory.lingvasferoapi.feature.region

import com.moineaufactory.lingvasferoapi.feature.language.admin.mapper.toRegionEntity
import data.dto.RegionDto
import data.mapper.toRegionDto
import data.service.RegionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/admin_api/region/")
class AdminRegionController @Autowired constructor(
    private val regionService: RegionService
) {

    @PostMapping("/add_edit_region")
    fun addEditRegion(
        @RequestPart("region") region: AddEditRegionDto,
        @RequestPart("flag") flag: MultipartFile?
    ): ResponseEntity<List<RegionDto>> {
        val oldRegion = region.id?.let { regionService.getById(it) }

        oldRegion?.let {
            if (oldRegion.iso != region.iso && flag == null) {
                println("Rewrite: ${oldRegion.iso} to ${region.iso}")
                val oldImageFile = Paths.get("img", "regions", "${oldRegion.iso}.png").toFile()
                val newImageFile = Paths.get("img", "regions", "${region.iso}.png").toFile()
                oldImageFile.renameTo(newImageFile)
            }
        }

        // Rewrite file only if images are different
        flag?.let {
            if (oldRegion?.imageHash != region.flagHash) {
                // Define the file path where the flag image will be stored
                val filePath = Paths.get("img", "languages", "${region.iso}.png")
                try {
                    // Create directories if they don't exist
                    Files.createDirectories(filePath.parent)
                    // Write the file to the specified path
                    Files.write(filePath, flag.bytes)
                } catch (ex: IOException) {
                    // Handle the case where file saving fails
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
                }
            }
        }

        regionService.save(region.toRegionEntity())

        val regions = regionService.getAll().map { it.toRegionDto() }
        return ResponseEntity(regions, HttpStatus.OK)
    }

}