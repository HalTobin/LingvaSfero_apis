package com.moineaufactory.lingvasferoapi.controller

import com.moineaufactory.lingvasferoapi.dto.ContentCreatorFilterDto
import com.moineaufactory.lingvasferoapi.dto.LanguagePreviewDto
import com.moineaufactory.lingvasferoapi.service.ContentCreatorService
import com.moineaufactory.lingvasferoapi.service.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException

@RestController
@RequestMapping("/public_api/language_preview")
class LanguagePreviewController @Autowired constructor(
    private val languageService: LanguageService,
    private val contentCreatorService: ContentCreatorService
) {

    @GetMapping("/list")
    fun getLanguageListPreview(): ResponseEntity<List<LanguagePreviewDto>> {
        val languages = languageService.getAll().mapNotNull { language ->
            language.id?.let { languageId ->
                LanguagePreviewDto(
                    name = language.name,
                    flag = "language_preview/image?iso=${language.iso}",
                    color = language.color,
                    supportLevel = language.supportLevel.numberId,
                    nbContent = contentCreatorService
                        .countContentCreatorsByLanguage(languageId = languageId)
                )
            }
        }
        return ResponseEntity(languages, HttpStatus.OK)
    }

    // TODO - Make reusable function (@ user_api/LanguageController.kt)
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

}