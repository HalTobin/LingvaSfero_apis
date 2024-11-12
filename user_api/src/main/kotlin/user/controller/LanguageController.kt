package com.moineaufactory.lingvasferoapi.feature.language.user.controller

import com.moineaufactory.lingvasferoapi.data.service.LanguageService
import com.moineaufactory.lingvasferoapi.data.dto.LanguageDto
import com.moineaufactory.lingvasferoapi.data.mapper.toLanguageDto
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
    private val languageService: LanguageService
) {

    // Get the flag that correspond to a language
    @GetMapping(value = ["/image"], params = ["iso"], produces = [MediaType.IMAGE_PNG_VALUE])
    @ResponseBody
    @Throws(IOException::class)
    fun getImage(@RequestParam iso: String): ResponseEntity<ByteArray> {
        return languageService.getByIso(iso)?.let { selectedLanguage ->
            println("Trying to open: ${selectedLanguage.imagePath}")
            val imgFile = File(selectedLanguage.imagePath)
            if (imgFile.exists()) try {
                val bytes = imgFile.readBytes()
                ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes)
            } catch (e: IOException) {
                println("Can't read file: ${selectedLanguage.imagePath}")
                ResponseEntity("Can't read file".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
            }
            else {
                println("File doesn't exist: ${selectedLanguage.imagePath}")
                ResponseEntity("File doesn't exist".toByteArray() ,HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } ?: ResponseEntity("Language not found".toByteArray(), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/all")
    fun getLanguages(): ResponseEntity<List<LanguageDto>> {
        val languages = languageService.getAll().map { it.toLanguageDto() }
        return ResponseEntity(languages, HttpStatus.OK)
    }

    // Get to /iso/:id that returns a language by id
    @GetMapping("/iso/{iso}")
    fun getLanguage(@PathVariable iso: String?): ResponseEntity<LanguageDto?> = iso?.let {
        val language = languageService.getByIso(iso)?.toLanguageDto()
        return ResponseEntity(language, language?.let { HttpStatus.OK } ?: HttpStatus.BAD_REQUEST)
    } ?: ResponseEntity(null, HttpStatus.BAD_REQUEST)

}