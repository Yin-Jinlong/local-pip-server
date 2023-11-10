package com.github.yinjinlong.service

import com.github.yinjinlong.io.FileIndex
import java.io.File

/**
 *
 * @author YJL
 */
interface WhlFileService : FileService {

    fun getIndex(group: String): Set<FileIndex>

    fun getMd5(file: File): String

    fun groups(): List<String>

    fun updateIndexes()

    operator fun get(group: String) = getIndex(group)

}