package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContentCreatorRepository : JpaRepository<ContentCreator?, Long?> {
    override fun findAll(): List<ContentCreator>
    override fun findById(id: Long): Optional<ContentCreator?>

    //@Query("SELECT * FROM region WHERE language_id = :languageId")
    fun findByLanguageId(languageId: Int): List<ContentCreator>

    //findByFilter...

    fun save(contentCreator: ContentCreator): ContentCreator
}