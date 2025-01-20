package com.moineaufactory.lingvasferoapi.feature.content.data.dto

data class UpdateContentDto(
    val lastUpdate: Long?,
    val channelsId: List<Long>
)