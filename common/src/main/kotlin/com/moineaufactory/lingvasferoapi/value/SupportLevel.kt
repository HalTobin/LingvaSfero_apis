package com.moineaufactory.lingvasferoapi.value

enum class SupportLevel(
    val numberId: Int,
    val textId: String
) {
    Full(0, "full"),
    Partial(1, "partial"),
    Poor(2, "poor")
}