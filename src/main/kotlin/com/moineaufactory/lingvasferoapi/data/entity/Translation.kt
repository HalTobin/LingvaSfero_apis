package com.moineaufactory.lingvasferoapi.data.entity

import jakarta.persistence.*
import java.io.Serializable

@Embeddable
class TranslationId(
    @Column(name = "text_id", nullable = false)
    val textId: String,

    @Column(name = "iso", nullable = false)
    val iso: String
) : Serializable

@Entity
@Table(name = "translation")
class Translation(
    @EmbeddedId
    val id: TranslationId,

    @Column(nullable = false)
    val text: String
)