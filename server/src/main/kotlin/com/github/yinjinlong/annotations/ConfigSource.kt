package com.github.yinjinlong.annotations

import com.github.yinjinlong.config.ConfigSourceFactory
import org.springframework.context.annotation.PropertySource
import org.springframework.core.annotation.AliasFor

/**
 * 配置源
 *
 * 支持properties,xml 和 yaml
 *
 * @author YJL
 */
@PropertySource(factory = ConfigSourceFactory::class)
annotation class ConfigSource(
    @get:AliasFor(annotation = PropertySource::class, attribute = "value")
    vararg val value: String
)
