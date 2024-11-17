package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.RegionDto
import com.moineaufactory.lingvasferoapi.mapper.toRegionDto
import com.moineaufactory.lingvasferoapi.service.RegionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/api/region")
class RegionController @Autowired constructor(
    private val regionService: RegionService
) {

    // Get the flag that correspond to a language
    @GetMapping(value = ["/image"], params = ["iso"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    @Throws(IOException::class)
    fun getImage(@RequestParam iso: String): ResponseEntity<ByteArray> {
        return regionService.getByIso(iso)?.let { selectedRegion ->
            println("Trying to open: ${selectedRegion.imagePath}")
            val imgFile = File(selectedRegion.imagePath)
            if (imgFile.exists()) try {
                val bytes = imgFile.readBytes()
                ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes)
            } catch (e: IOException) {
                println("Can't read file: ${selectedRegion.imagePath}")
                ResponseEntity("Can't read file".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
            }
            else {
                println("File doesn't exist: ${selectedRegion.imagePath}")
                ResponseEntity("File doesn't exist".toByteArray() ,HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } ?: ResponseEntity("Language not found".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/all")
    fun getRegions(): ResponseEntity<List<RegionDto>> {
        val regions = regionService.getAll().map { it.toRegionDto() }
        return ResponseEntity(regions, HttpStatus.OK)
    }

    @GetMapping("/find")
    fun getRegionsByLanguageId(@RequestParam("language_id") languageId: Int): ResponseEntity<List<RegionDto>> {
        val regions = regionService.getByLanguageId(languageId).map { it.toRegionDto() }
        return ResponseEntity(regions, HttpStatus.OK)
    }

    // Get to /iso/:id that returns a language by id
    @GetMapping("/iso/{iso}")
    fun getRegion(@PathVariable iso: String?): ResponseEntity<RegionDto?> = iso?.let {
        val region = regionService.getByIso(iso)?.toRegionDto()
        return ResponseEntity(region, region?.let { HttpStatus.OK } ?: HttpStatus.BAD_REQUEST)
    } ?: ResponseEntity(null, HttpStatus.BAD_REQUEST)

}