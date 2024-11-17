package com.moineaufactory.lingvasferoapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "content_creator")
class ContentCreator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val languageId: Int,
    val regionId: Int?,

    val thumbnail: String,
    val thumbnailSmall: String?,
    val name: String,
    val description: String,
    val levelMin: Int,
    val levelMax: Int,
)