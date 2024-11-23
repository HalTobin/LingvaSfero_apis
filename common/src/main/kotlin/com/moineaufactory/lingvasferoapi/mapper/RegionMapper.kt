package com.moineaufactory.lingvasferoapi.mapper

import com.moineaufactory.lingvasferoapi.dto.RegionDto
import com.moineaufactory.lingvasferoapi.entity.Region

fun Region.toRegionDto() = RegionDto(
    id = this.id!!,
    languageId = this.languageId,
    iso = this.iso,
    name = this.name,
    imageUrl = "api/region/image?iso=${this.iso}",
    imageHash = this.imageHash,
    color = this.color,
    lightColor = this.lightColor,
    darkColor = this.darkColor
)