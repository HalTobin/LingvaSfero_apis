package com.moineaufactory.lingvasferoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("api.properties")
class AdminApi

fun main(args: Array<String>) {
	runApplication<AdminApi>(*args)
}
