package com.moineaufactory.lingvasferoapi.data.model

import com.moineaufactory.lingvasferoapi.data.value.SupportLevel
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Language(
    @Id val iso: String,
    val name: String,
    val flagUrl: String,
    val flagHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long,
    val supportLevel: SupportLevel,
)