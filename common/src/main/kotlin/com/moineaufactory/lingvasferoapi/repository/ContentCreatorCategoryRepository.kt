package com.moineaufactory.lingvasferoapi.repository

import com.moineaufactory.lingvasferoapi.entity.ContentCreatorCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ContentCreatorCategoryRepository : JpaRepository<ContentCreatorCategory?, Long?> {
    override fun findAll(): List<ContentCreatorCategory>

    @Query("SELECT c FROM ContentCreatorCategory c WHERE c.id.contentCreatorId = :id")
    fun findByContentCreatorId(id: Long): List<ContentCreatorCategory>

    @Modifying
    @Query("DELETE FROM ContentCreatorCategory c WHERE c.id.contentCreatorId = :id")
    fun deleteByContentCreatorId(id: Long)

    @Modifying
    @Query(
        """
    DELETE FROM ContentCreatorCategory c 
    WHERE c.id.contentCreatorId = :contentCreatorId
    AND c.id.categoryId NOT IN (:newCategoryIds)
    """
    )
    fun deleteNotInList(
        @Param("contentCreatorId") contentCreatorId: Long,
        @Param("newCategoryIds") newCategoryIds: List<Int>
    ): Int

    fun save(contentCreatorCategory: ContentCreatorCategory): ContentCreatorCategory
    //fun saveAll(contentCreatorCategoryList: List<ContentCreatorCategory>)
}