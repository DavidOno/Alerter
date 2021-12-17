package de.boettcher.alerter.core

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
@SpringBootApplication
class CoreApplication

fun main(args: Array<String>) {
	runApplication<CoreApplication>(*args)
}
