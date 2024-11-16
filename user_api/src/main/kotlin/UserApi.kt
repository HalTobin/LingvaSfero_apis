package com.moineaufactory.lingvasferoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserApi

fun main(args: Array<String>) {
	runApplication<UserApi>(*args)
}
