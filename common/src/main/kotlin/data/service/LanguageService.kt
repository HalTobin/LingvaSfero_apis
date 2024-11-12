package com.moineaufactory.lingvasferoapi.data.service

import com.moineaufactory.lingvasferoapi.data.entity.Language
import com.moineaufactory.lingvasferoapi.data.repository.LanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LanguageService @Autowired constructor(
    private val languageRepository: LanguageRepository
) {
    fun getAll(): List<Language> = languageRepository.findAll()
    fun getById(id: Int): Language? = languageRepository.findById(id).orElse(null)
    fun getByIso(iso: String): Language? = languageRepository.findByIso(iso).orElse(null)
    fun save(language: Language): Language = languageRepository.save(language)
}