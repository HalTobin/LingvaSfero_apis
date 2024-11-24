package com.moineaufactory.lingvasferoapi.dto

data class ChannelDto(
    val id: Int,
    val contentCreatorId: Long,
    val title: String?,
    val source: ChannelSourceDto,
    val sourceLink: String
)