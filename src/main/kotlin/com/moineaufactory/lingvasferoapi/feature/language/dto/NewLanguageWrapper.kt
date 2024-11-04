package com.moineaufactory.lingvasferoapi.feature.language.dto

import org.springframework.web.multipart.MultipartFile

data class NewLanguageWrapper(
    val flag: MultipartFile,
    val language: AddEditLanguageDto
)