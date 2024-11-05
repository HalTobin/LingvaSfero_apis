package com.moineaufactory.lingvasferoapi.feature.language.controller

import com.moineaufactory.lingvasferoapi.data.repository.LanguageRepository
import com.moineaufactory.lingvasferoapi.feature.language.dto.LanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.mapper.toLanguageDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/api/language")
class LanguageController @Autowired constructor(
    private val languageRepository: LanguageRepository
) {

    // Get the flag that correspond to a language
    @GetMapping(value = ["/image"], params = ["iso"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    @Throws(IOException::class)
    fun getImage(@RequestParam iso: String): ResponseEntity<ByteArray> {
        return languageRepository.findByIso(iso)?.let { selectedLanguage ->
            println("Trying to open: ${selectedLanguage.flagUrl}")
            val imgFile = File(selectedLanguage.flagUrl)
            if (imgFile.exists()) try {
                val bytes = imgFile.readBytes()
                ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes)
            } catch (e: IOException) {
                println("Can't read file: ${selectedLanguage.flagUrl}")
                ResponseEntity("Can't read file".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
            }
            else {
                println("File doesn't exist: ${selectedLanguage.flagUrl}")
                ResponseEntity("File doesn't exist".toByteArray() ,HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } ?: ResponseEntity("Language not found".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/all")
    fun getLanguages(): ResponseEntity<List<LanguageDto>> {
        val languages = languageRepository.findAll().map { it.toLanguageDto() }
        return ResponseEntity(languages, HttpStatus.OK)
    }

    // Get to /iso/:id that returns a language by id
    @GetMapping("/iso/{iso}")
    fun getLanguage(@PathVariable iso: String?): ResponseEntity<LanguageDto?> {
        val language = languageRepository.findByIso(iso)?.toLanguageDto()
        return ResponseEntity(language, language?.let { HttpStatus.OK } ?: HttpStatus.BAD_REQUEST)
    }
}