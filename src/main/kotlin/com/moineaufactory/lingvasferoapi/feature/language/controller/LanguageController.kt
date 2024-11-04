package com.moineaufactory.lingvasferoapi.feature.language.controller

import com.moineaufactory.lingvasferoapi.data.repository.LanguageRepository
import com.moineaufactory.lingvasferoapi.feature.language.dto.LanguageDto
import com.moineaufactory.lingvasferoapi.feature.language.mapper.toLanguageDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/api/language")
class LanguageController @Autowired constructor(
    private val languageRepository: LanguageRepository
) {

    // Get the flag that correspond to a language
    @GetMapping(value = ["/image"], params = ["iso"], produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseBody
    @Throws(
        IOException::class
    )
    fun getImage(@RequestParam iso: String): ResponseEntity<ByteArray> {
        var path = "img/languages/globe.png"
        if (iso == "ENG") path = "img/languages/gb.png"
        if (iso == "ESP") path = "img/languages/es.png"
        if (iso == "FRA") path = "img/languages/fr.png"
        if (iso == "ITA") path = "img/languages/it.png"
        if (iso == "KAZ") path = "img/languages/kz.png"
        if (iso == "RUS") path = "img/languages/ru.png"
        if (iso == "UKR") path = "img/languages/ua.png"
        if (iso == "DEU") path = "img/languages/de.png"
        if (iso == "POR") path = "img/languages/pt.png"
        if (iso == "CHN") path = "img/languages/cn.png"
        if (iso == "TUR") path = "img/languages/tr.png"
        if (iso == "JAP") path = "img/languages/jp.png"
        if (iso == "ARA") path = "img/languages/ar.png"
        if (iso == "KOR") path = "img/languages/kr.png"
        if (iso == "EPO") path = "img/languages/eo.png"
        if (iso == "CAT") path = "img/languages/ct.png"
        if (iso == "CZE") path = "img/languages/cz.png"
        if (iso == "OCI") path = "img/languages/oc.png"
        if (iso == "POL") path = "img/languages/pl.png"
        if (iso == "NLD") path = "img/languages/nl.png"

        val imgFile = ClassPathResource(path)
        val bytes = StreamUtils.copyToByteArray(imgFile.inputStream)

        return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(bytes)
    }

    // Get to /languages that returns list of languages
    @GetMapping("/languages")
    fun getLanguages(): ResponseEntity<List<LanguageDto>> {
        val languages = languageRepository.findAll().map { it.toLanguageDto() }
        return ResponseEntity(languages, HttpStatus.OK)
    }

    // Get to /languages/:id that returns a language by id
    @GetMapping(value = ["/languages"], params = ["iso"])
    fun getLanguage(@RequestParam iso: String?): ResponseEntity<LanguageDto?> {
        val language = languageRepository.findByIso(iso)?.toLanguageDto()
        return ResponseEntity(language, HttpStatus.OK)
    }
}