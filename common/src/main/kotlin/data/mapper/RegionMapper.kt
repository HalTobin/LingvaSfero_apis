package data.mapper

import data.dto.RegionDto
import data.entity.Region

fun Region.toRegionDto() = RegionDto(
    id = this.id!!,
    languageId = this.languageId,
    iso = this.iso,
    name = this.name,
    imageUrl = this.imagePath,
    imageHash = this.imageHash,
    color = this.color,
    lightColor = this.lightColor,
    darkColor = this.darkColor
)