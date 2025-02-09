package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.Language
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LanguageRepository : JpaRepository<Language?, Int?> {
    override fun findAll(): List<Language>
    override fun findById(id: Int): Optional<Language?>
    fun findByIso(iso: String): Optional<Language?>
    fun save(language: Language): Language
}