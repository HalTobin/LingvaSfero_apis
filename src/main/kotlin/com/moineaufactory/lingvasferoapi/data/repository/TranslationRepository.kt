package com.moineaufactory.lingvasferoapi.data.repository

import com.moineaufactory.lingvasferoapi.data.entity.Translation
import com.moineaufactory.lingvasferoapi.data.entity.TranslationId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TranslationRepository : JpaRepository<Translation, TranslationId> {
    override fun findAll(): List<Translation>

    // Get a list of translations by the Category textId
    @Query("SELECT t FROM Translation t WHERE t.id.textId = :textId")
    fun findByTextId(@Param("textId") textId: String): List<Translation>

    // Save or update translation entries
    fun save(entity: Translation): Translation
}