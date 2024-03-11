package com.gava.rinhav2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class Rinhav2Application

fun main(args: Array<String>) {
	runApplication<Rinhav2Application>(*args)
}
