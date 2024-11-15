package data.dto

data class RegionDto(
    val id: Int,
    val languageId: Int,

    val iso: String,
    val name: String,
    val imageUrl: String,
    val imageHash: Int,
    val color: Long,
    val lightColor: Long,
    val darkColor: Long
)