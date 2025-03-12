package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.ContentCreator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ContentCreatorRepository : JpaRepository<ContentCreator?, Long?> {
    override fun findAll(): List<ContentCreator>
    override fun findById(id: Long): Optional<ContentCreator?>

    fun findByLanguageId(languageId: Int): List<ContentCreator>


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

    @Query(
        value = """
            WITH category_array AS (
                SELECT UNNEST(:categories_id) AS category_id
            )
            SELECT DISTINCT cc.* 
            FROM content_creator cc
            LEFT JOIN content_creator_category ccc ON cc.id = ccc.content_creator_id
            LEFT JOIN channel ch ON ch.content_creator_id = cc.id
            WHERE (:language_id IS NULL OR cc.language_id = :language_id)
              AND (:region_id IS NULL OR cc.region_id = :region_id)
              AND (
                  :array_length = 0
                  OR (
                      SELECT COUNT(*) 
                      FROM content_creator_category ccc_sub
                      JOIN category_array ca ON ccc_sub.category_id = ca.category_id
                      WHERE ccc_sub.content_creator_id = cc.id
                  ) = :array_length
              )
              AND (:level_id IS NULL OR (cc.level_min <= :level_id AND cc.level_max >= :level_id))
              AND (:channel_source_id IS NULL OR ch.source_id = :channel_source_id)
            ORDER BY cc.id ASC
        """,
        nativeQuery = true
    )
    fun filterContentCreator(
        @Param("language_id") languageId: Int?,
        @Param("region_id") regionId: Int?,
        @Param("categories_id") categoriesId: Array<Int>,
        @Param("array_length") arrayLength: Int,
        @Param("channel_source_id") channelSourceId: Int?,
        @Param("level_id") levelId: Int?
    ): List<ContentCreator>

    @Query(
        value = """
            WITH category_array AS (
                SELECT UNNEST(:categories_id) AS category_id
            )
            SELECT DISTINCT cc.* 
            FROM content_creator cc
            LEFT JOIN content_creator_category ccc ON cc.id = ccc.content_creator_id
            LEFT JOIN channel ch ON ch.content_creator_id = cc.id
            WHERE (:language_id IS NULL OR cc.language_id = :language_id)
              AND (:region_id IS NULL OR cc.region_id = :region_id)
              AND (
                  :array_length = 0
                  OR (
                      SELECT COUNT(*) 
                      FROM content_creator_category ccc_sub
                      JOIN category_array ca ON ccc_sub.category_id = ca.category_id
                      WHERE ccc_sub.content_creator_id = cc.id
                  ) = :array_length
              )
              AND (:level_id IS NULL OR (cc.level_min <= :level_id AND cc.level_max >= :level_id))
              AND (:channel_source_id IS NULL OR ch.source_id = :channel_source_id)
            ORDER BY cc.id ASC
        """,
        countQuery = """
            WITH category_array AS (
                SELECT UNNEST(:categories_id) AS category_id
            )
            SELECT COUNT(DISTINCT cc.id)
            FROM content_creator cc
            LEFT JOIN content_creator_category ccc ON cc.id = ccc.content_creator_id
            LEFT JOIN channel ch ON ch.content_creator_id = cc.id
            WHERE (:language_id IS NULL OR cc.language_id = :language_id)
                AND (:region_id IS NULL OR cc.region_id = :region_id)
                AND (
                    :array_length = 0
                    OR (
                      SELECT COUNT(*) 
                      FROM content_creator_category ccc_sub
                      JOIN category_array ca ON ccc_sub.category_id = ca.category_id
                      WHERE ccc_sub.content_creator_id = cc.id
                    ) = :array_length
                )
                AND (:level_id IS NULL OR (cc.level_min <= :level_id AND cc.level_max >= :level_id))
                AND (:channel_source_id IS NULL OR ch.source_id = :channel_source_id)
            """,
        nativeQuery = true
    )
    fun filterContentCreatorPage(
        @Param("language_id") languageId: Int?,
        @Param("region_id") regionId: Int?,
        @Param("categories_id") categoriesId: Array<Int>,
        @Param("array_length") arrayLength: Int,
        @Param("channel_source_id") channelSourceId: Int?,
        @Param("level_id") levelId: Int?,
        pageable: Pageable
    ): Page<ContentCreator>

    @Query(
        value =
        """
            WITH category_array AS (
                SELECT UNNEST(:categories_id) AS category_id
            )
            SELECT COUNT(DISTINCT cc.id)
            FROM content_creator cc
            LEFT JOIN content_creator_category ccc ON cc.id = ccc.content_creator_id
            LEFT JOIN channel ch ON ch.content_creator_id = cc.id
            WHERE (:language_id IS NULL OR cc.language_id = :language_id)
                AND (:region_id IS NULL OR cc.region_id = :region_id)
                AND (
                    :array_length = 0
                    OR (
                      SELECT COUNT(*) 
                      FROM content_creator_category ccc_sub
                      JOIN category_array ca ON ccc_sub.category_id = ca.category_id
                      WHERE ccc_sub.content_creator_id = cc.id
                    ) = :array_length
                )
                AND (:level_id IS NULL OR (cc.level_min <= :level_id AND cc.level_max >= :level_id))
                AND (:channel_source_id IS NULL OR ch.source_id = :channel_source_id)
            """,
        nativeQuery = true
    )
    fun countContentCreatorByFilter(
        @Param("language_id") languageId: Int?,
        @Param("region_id") regionId: Int?,
        @Param("categories_id") categoriesId: Array<Int>,
        @Param("array_length") arrayLength: Int,
        @Param("channel_source_id") channelSourceId: Int?,
        @Param("level_id") levelId: Int?
    ): Int

    @Query("""
            SELECT COUNT(DISTINCT cc.id)
            FROM content_creator cc
            WHERE cc.language_id = :language_id
        """,
        nativeQuery = true)
    fun countContentCreatorByLanguageId(
        @Param("language_id") languageId: Int
    ): Int

    fun save(contentCreator: ContentCreator): ContentCreator

    @Query("SELECT cc.* FROM content_creator cc " +
            "JOIN language l ON cc.language_id = l.id " +
            "LEFT JOIN region r ON cc.region_id = r.id " +
            "WHERE " +
            "LOWER(cc.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(cc.description) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(l.iso) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(r.iso) LIKE LOWER(CONCAT('%', :search, '%'));",
        nativeQuery = true)
            //"LOWER(r.iso) LIKE LOWER(CONCAT('%', :search, '%'))")
            //"WHERE cc.name || cc.description || l.id || r.id " +
            //"LIKE '%' || :search || '%'")
    fun findContentCreatorBySearchText(@Param("search") search: String): List<ContentCreator>

}