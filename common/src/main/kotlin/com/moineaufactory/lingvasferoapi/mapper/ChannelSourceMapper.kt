package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.dto.ChannelSourceDto
import com.moineaufactory.lingvasferoapi.value.ChannelSource

fun ChannelSource.toChannelSourceDto() = ChannelSourceDto(
    id = this.id,
    title = this.title,
    imageColor = "api/channel/source_image?id=${this.id}&color=true",
    imageBw = "api/channel/source_image?id=${this.id}&color=false",
    color = this.color
)