package com.github.yinjinlong

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller

@Controller
@SpringBootApplication
class MainApp {

}

fun main(vararg args: String) {
    runApplication<MainApp>(*args)
}