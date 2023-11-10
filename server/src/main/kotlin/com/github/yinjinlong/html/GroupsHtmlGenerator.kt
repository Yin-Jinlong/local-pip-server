package com.github.yinjinlong.html

import com.github.yinjinlong.service.WhlFileService
import java.io.File

/**
 *
 * @author YJL
 */
object GroupsHtmlGenerator {

    val header = """
         <style>th{text-align: left;}td:nth-child(1){padding-right: 2em;}</style>
        <table><thead><tr><th>Name</th><th>Count</th></tr></thead><tbody>

    """.trimIndent().toByteArray()

    private fun line(name: String, count: Int): String {
        return "<tr><td><a href='/simple/$name'>$name</a><br></td><td>$count</td></tr>\n"
    }

    fun generate(service:WhlFileService, file: File) {
        file.outputStream().use { o ->
            o.write(header)
            for (g in service.groups()) {
                o.write(line(g, service[g].size).toByteArray())
            }
        }
    }
}