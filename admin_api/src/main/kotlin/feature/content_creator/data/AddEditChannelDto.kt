package com.moineaufactory.lingvasferoapi.feature.content_creator.data

data class AddEditChannelDto(
    val id: Long?,
    val contentCreatorId: Long,
    val sourceId: Int,
    val sourceLink: String,
    val title: String,
    val description: String
)