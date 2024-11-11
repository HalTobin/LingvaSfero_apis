package com.moineaufactory.lingvasferoapi.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable


@Embeddable
data class TranslationId(
    @Column(name = "text_id", nullable = false)
    val textId: String,

    @Column(name = "iso", nullable = false)
    val iso: String
) : Serializable