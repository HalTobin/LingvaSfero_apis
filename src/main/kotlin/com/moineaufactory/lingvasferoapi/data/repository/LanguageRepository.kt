package com.moineaufactory.lingvasferoapi.data.repository

import com.moineaufactory.lingvasferoapi.data.model.Language
import org.springframework.data.jpa.repository.JpaRepository

interface LanguageRepository : JpaRepository<Language?, Int?> {
    override fun findAll(): List<Language>
    fun findByIso(iso: String?): Language?
    fun save(language: Language): Language
}