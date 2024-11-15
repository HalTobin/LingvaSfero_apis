package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.Language
import com.moineaufactory.lingvasferoapi.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface RegionRepository : JpaRepository<Region?, Int?> {
    override fun findAll(): List<Region>
    override fun findById(id: Int): Optional<Region?>

    //@Query("SELECT * FROM region WHERE language_id = :languageId")
    fun findByLanguageId(languageId: Int): List<Region>

    fun findByIso(iso: String): Optional<Region?>
    fun save(language: Language): Region
}