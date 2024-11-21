package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.dto.FullContentCreatorDto
import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ContentCreatorRepository : JpaRepository<ContentCreator?, Long?> {
    override fun findAll(): List<ContentCreator>
    override fun findById(id: Long): Optional<ContentCreator?>

    //@Query("SELECT * FROM region WHERE language_id = :languageId")
    fun findByLanguageId(languageId: Int): List<ContentCreator>

    //findByFilter...

    @Query(
        value = """
    SELECT 
        cc.id AS contentCreatorId,
            l.id AS languageId, 
            l.iso AS languageIso,
            l.name AS languageName, 
            l.image_path AS languageImagePath,
            l.image_hash AS languageImageHash, 
            l.color AS languageColor,
            l.light_color AS languageLightColor, 
            l.dark_color AS languageDarkColor, 
            l.support_level AS languageSupportLevel,
            r.id AS regionId,
            r.language_id AS regionLanguageId,
            r.iso AS regionIso, 
            r.name AS regionName,
            r.image_path AS regionImagePath, 
            r.image_hash AS regionImageHash,
            r.color AS regionColor, 
            r.light_color AS regionLightColor,
            r.dark_color AS regionDarkColor,
        cc.thumbnail AS thumbnail,
        cc.thumbnail_small AS thumbnailSmall,
        cc.name AS creatorName,
        cc.description AS creatorDescription,
        cc.level_min AS levelMin, 
        cc.level_max AS levelMax,
            c.id AS categoryId,
            c.text_id AS categoryTextId, 
            t.iso AS translationIso,
            t.text AS translationText
    FROM content_creator cc
    JOIN language l ON cc.language_id = l.id
    LEFT JOIN region r ON cc.region_id = r.id
    LEFT JOIN content_creator_category ccc ON ccc.content_creator_id = cc.id
    LEFT JOIN category c ON c.id = ccc.category_id
    LEFT JOIN translation t ON t.text_id = c.text_id
    WHERE cc.id = :id
    """,
        nativeQuery = true
    )
    fun findFullContentCreatorById(@Param("id") id: Long): List<Map<String, Any>>

    fun save(contentCreator: ContentCreator): ContentCreator
}