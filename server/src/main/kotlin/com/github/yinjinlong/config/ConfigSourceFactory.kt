package com.github.yinjinlong.config

import org.springframework.boot.env.PropertiesPropertySourceLoader
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.util.*

/**
 *
 * @author YJL
 */
class ConfigSourceFactory:PropertySourceFactory {

    private val yamlLoader= YamlPropertySourceLoader()

    private val properties= PropertiesPropertySourceLoader()

    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val fileName = resource.resource.filename ?: throw NullPointerException("Property file is null")
        val i = fileName.lastIndexOf(".")
        if (i < 0)
            throw RuntimeException("File name must contain '.'")

        return when (val ext = fileName.substring(i + 1).lowercase(Locale.getDefault())){
            "yaml"->yamlLoader.load(name,resource.resource)[0]
            "xml","properties"->properties.load(name,resource.resource)[0]
            else->throw RuntimeException("File type not supported: $ext")
        }
    }
}