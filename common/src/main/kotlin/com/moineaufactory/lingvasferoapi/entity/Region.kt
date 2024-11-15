package com.moineaufactory.lingvasferoapi.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Region(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    val languageId: Int,

    val iso: String,
    val name: String,
    val imagePath: String,
    val imageHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long,
)