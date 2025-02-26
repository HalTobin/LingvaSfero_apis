package com.moineaufactory.lingvasferoapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public_api/basics")
class PingController {
    @GetMapping("/ping")
    fun ping(): ResponseEntity<Boolean> = ResponseEntity(true, HttpStatus.OK)
}