package com.github.yinjinlong.config

import java.io.File


class PipConfig {

    companion object {
        var bufferSize: Int = 4 * 1024
        lateinit var baseDir: File
    }

}