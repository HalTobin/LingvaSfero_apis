package com.moineaufactory.lingvasferoapi.api.dto

data class ChannelPreviewDto(
    val channelId: String,
    val title: String,
    val description: String,
    val image: String?
)