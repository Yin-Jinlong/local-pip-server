package com.github.yinjinlong.service

import java.io.File

/**
 *
 * @author YJL
 */
interface FileService {

    fun getFile(fileName: String): File

    fun getGroup(fileName: String): String

}