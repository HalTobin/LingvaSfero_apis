package com.moineaufactory.lingvasferoapi.feature.content_creator.data.dto

data class AddEditChannelDto(
    val id: Long?,
    val contentCreatorId: Long,
    val sourceId: Int,
    val sourceLink: String,
    val website: String,
    val title: String,
    val description: String
)