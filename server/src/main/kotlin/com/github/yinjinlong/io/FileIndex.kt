package com.github.yinjinlong.io

import java.io.File

/**
 *
 * @author YJL
 */
data class FileIndex(
    val name: String,
    val size: String,
    val file: File
)