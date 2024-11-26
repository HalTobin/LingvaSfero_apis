package com.moineaufactory.lingvasferoapi.dto

data class ChannelDto(
    val id: Long,
    val contentCreatorId: Long,
    val title: String?,
    val description: String?,
    val source: ChannelSourceDto,
    val sourceLink: String
)