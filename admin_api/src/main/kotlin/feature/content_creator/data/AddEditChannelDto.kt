package com.moineaufactory.lingvasferoapi.feature.content_creator.data

data class AddEditChannelDto(
    val id: Int?,
    val contentCreatorId: Long,
    val title: String,
    val sourceId: Int,
    val sourceLink: String
)