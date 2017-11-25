package dk.renner.website

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WebsiteApplication

fun main(args: Array<String>) {
    runApplication<WebsiteApplication>(*args)
}

