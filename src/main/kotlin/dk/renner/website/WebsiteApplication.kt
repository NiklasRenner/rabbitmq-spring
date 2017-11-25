package dk.renner.website

import dk.renner.website.control.GenericService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import javax.annotation.PostConstruct
import kotlin.collections.HashMap
import kotlin.system.measureTimeMillis

@SpringBootApplication
class WebsiteApplication(val genericService: GenericService) {

    @PostConstruct
    fun start() = genericService.doStuff()

}

fun main(args: Array<String>) {
    runApplication<WebsiteApplication>(*args)
}

fun <T> T.log(prefix: String = ""): T = this.also { println(prefix + it) }

