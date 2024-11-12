package com.moineaufactory.lingvasferoapi.feature.language.admin.controller

import com.moineaufactory.lingvasferoapi.data.service.LanguageService
import com.moineaufactory.lingvasferoapi.feature.language.admin.dto.AddEditLanguageDto
import com.moineaufactory.lingvasferoapi.data.dto.LanguageDto
import com.moineaufactory.lingvasferoapi.data.mapper.toLanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.admin.mapper.toLanguageEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/admin_api/language/")
class AdminLanguageController @Autowired constructor(
    private val languageService: LanguageService
) {

    @PostMapping("/add_edit_language")
    fun addEditLanguage(
        @RequestPart("language") language: AddEditLanguageDto,
        @RequestPart("flag") flag: MultipartFile?
    ): ResponseEntity<List<LanguageDto>> {
        val oldLanguage = language.id?.let { languageService.getById(it) }

        oldLanguage?.let {
            if (oldLanguage.iso != language.iso && flag == null) {
                println("Rewrite: ${oldLanguage.iso} to ${language.iso}")
                val oldImageFile = Paths.get("img", "languages", "${oldLanguage.iso}.png").toFile()
                val newImageFile = Paths.get("img", "languages", "${language.iso}.png").toFile()
                oldImageFile.renameTo(newImageFile)
            }
        }

        // Rewrite file only if images are different
        flag?.let {
            if (oldLanguage?.imageHash != language.flagHash) {
                // Define the file path where the flag image will be stored
                val filePath = Paths.get("img", "languages", "${language.iso}.png")
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

        languageService.save(language.toLanguageEntity())

        val languages = languageService.getAll().map { it.toLanguageDto() }
        return ResponseEntity(languages, HttpStatus.OK)
    }

}