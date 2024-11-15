package com.moineaufactory.lingvasferoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.moineaufactory.lingvasferoapi", "com"])
@SpringBootApplication
class UserApi

fun main(args: Array<String>) {
	runApplication<UserApi>(*args)
}
