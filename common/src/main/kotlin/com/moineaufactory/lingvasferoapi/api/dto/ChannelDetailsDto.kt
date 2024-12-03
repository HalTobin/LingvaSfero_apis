package com.moineaufactory.lingvasferoapi.api.dto

import com.moineaufactory.lingvasferoapi.value.ChannelSource

data class ChannelDetailsDto(
    val channelId: String,
    val title: String,
    val description: String,
    val source: ChannelSource,
    val website: String,
    val thumbnail: String?,
    val thumbnailSmall: String?
)