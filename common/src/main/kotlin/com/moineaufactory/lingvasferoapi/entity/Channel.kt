package com.moineaufactory.lingvasferoapi.entity

import jakarta.persistence.*

@Entity
@Table(name = "channel")
class Channel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val contentCreatorId: Long,

    val title: String?,
    val description: String?,
    val sourceId: Int,
    val sourceLink: String,

    // Timestamp corresponding to the last update of the channel's content
    val contentDate: Long
)