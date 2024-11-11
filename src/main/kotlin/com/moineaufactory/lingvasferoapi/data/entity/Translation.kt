package com.moineaufactory.lingvasferoapi.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "translation")
data class Translation(
    @EmbeddedId
    val id: TranslationId,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_id", insertable = false, updatable = false)
    val category: Category,

    @Column(nullable = false)
    val text: String
)