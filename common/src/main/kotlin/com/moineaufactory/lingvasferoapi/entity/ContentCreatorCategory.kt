package com.moineaufactory.lingvasferoapi.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.io.Serializable

@Embeddable
class ContentCreatorCategoryId(
    val categoryId: Int,
    val contentCreatorId: Long
) : Serializable

@Entity
@Table(name = "content_creator_category")
class ContentCreatorCategory(
    @EmbeddedId
    val id: ContentCreatorCategoryId
)