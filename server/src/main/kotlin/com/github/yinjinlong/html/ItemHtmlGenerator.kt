package com.github.yinjinlong.html

import com.github.yinjinlong.service.WhlFileService
import java.io.File

/**
 *
 * @author YJL
 */
object ItemHtmlGenerator {

    val header = """
         <style>th{text-align: left;}td:nth-child(1){padding-right: 2em;}</style>
        <table><thead><tr><th>Name</th><th>Size</th></tr></thead><tbody>

    """.trimIndent().toByteArray()

    private fun line(name: String, md5: String, size: String): String {
        return "<tr><td><a href='/simple/download/$name#md5=${md5}'>$name</a><br></td><td>$size</td></tr>\n"
    }

    fun generate(service: WhlFileService, name: String, file: File) {
        file.outputStream().use { o ->
            o.write(header)
            for (i in service[name]) {
                o.write(line(i.name, service.getMd5(i.file), i.size).toByteArray())
            }
        }
    }
}