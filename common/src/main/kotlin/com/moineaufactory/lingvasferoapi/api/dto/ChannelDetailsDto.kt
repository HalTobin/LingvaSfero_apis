package com.moineaufactory.lingvasferoapi.api.dto

import com.moineaufactory.lingvasferoapi.dto.ChannelSourceDto

data class ChannelDetailsDto(
    val channelId: String,
    val title: String,
    val description: String,
    val source: ChannelSourceDto,
    val website: String,
    val thumbnail: String?,
    val thumbnailSmall: String?
)