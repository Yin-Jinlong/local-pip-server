package com.github.yinjinlong.config

import com.github.yinjinlong.annotations.ConfigSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.File

/**
 *
 * @author YJL
 */
@Configuration
@ConfigSource("file:./config/app.yaml")
class WebConfig(
    @Value("\${pip.buffer}")
    bufferSize: String,
    @Value("\${pip.dir}")
    pipDir: String
) : WebMvcConfigurer {

    init {
        val sizeRegex = Regex("(\\d+)([kKmM])")
        val gs = sizeRegex.find(bufferSize)?.groupValues
            ?: throw IllegalArgumentException("bufferSize must be a number followed by k or m")
        val size = gs[1].toInt()
        val units = when (gs[2].toLowerCase()) {
            "k" -> 1024
            "m" -> 1024 * 1024
            else -> throw IllegalArgumentException("bufferSize must be a number followed by k or m")
        }
        PipConfig.bufferSize = size * units
        PipConfig.baseDir = File(pipDir)
    }

}