package com.moineaufactory.lingvasferoapi.feature.language.controller

import com.moineaufactory.lingvasferoapi.data.repository.LanguageRepository
import com.moineaufactory.lingvasferoapi.feature.language.dto.AddEditLanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.dto.LanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.mapper.toLanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.mapper.toLanguageEntity
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
    private val languageRepository: LanguageRepository
) {

    @PostMapping("/add_edit_language")
    fun addEditLanguage(
        @RequestPart("language") language: AddEditLanguageDto,
        @RequestPart("flag") flag: MultipartFile
    ): ResponseEntity<List<LanguageDto>> {
        languageRepository.save(language.toLanguageEntity())

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

        val languages = languageRepository.findAll().map { it.toLanguageDto() }
        return ResponseEntity(languages, HttpStatus.OK)
    }

}