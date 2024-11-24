package com.moineaufactory.lingvasferoapi.entity

import com.moineaufactory.lingvasferoapi.value.ChannelSource
import jakarta.persistence.*

@Entity
@Table(name = "channel")
class Channel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val contentCreatorId: Long,

    val title: String,
    val source: ChannelSource,
    val sourceLink: String,

    // Timestamp corresponding to the last update of the channel's content
    val contentDate: Long
)