package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.Translation
import com.moineaufactory.lingvasferoapi.entity.TranslationId
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TranslationRepository : JpaRepository<Translation, TranslationId> {
    override fun findAll(): List<Translation>

    // Get a list of translations by the Category textId
    @Query("SELECT t FROM Translation t WHERE t.id.textId = :textId")
    fun findAllByTextId(@Param("textId") textId: String): List<Translation>

    // Save or update translation entries
    fun save(entity: Translation): Translation

    // Method to update all translations with the specified oldTextId to newTextId
    @Modifying
    @Transactional
    @Query("UPDATE Translation t SET t.id.textId = :newTextId WHERE t.id.textId = :oldTextId")
    fun updateTextIdForTranslations(oldTextId: String, newTextId: String): Int
}