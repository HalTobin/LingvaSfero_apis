package com.moineaufactory.lingvasferoapi.data.entity

import com.moineaufactory.lingvasferoapi.data.value.SupportLevel
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Language(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    val iso: String,
    val name: String,
    val imagePath: String,
    val imageHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long,
    val supportLevel: SupportLevel,
)