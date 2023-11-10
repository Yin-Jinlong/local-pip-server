package com.github.yinjinlong.service.impl

import com.github.yinjinlong.config.PipConfig
import com.github.yinjinlong.io.FileIndex
import com.github.yinjinlong.security.md5
import com.github.yinjinlong.service.WhlFileService
import com.github.yinjinlong.util.sync
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException
import java.text.Collator
import java.util.*

/**
 *
 * @author YJL
 */
@Service
class WhlFileServiceImpl : WhlFileService {

    private val indexes = HashMap<String, Set<FileIndex>>()

    private var groups: List<String> = emptyList()

    init {
        updateIndexes()
    }

    override fun groups(): List<String> = groups

    override fun getIndex(group: String): Set<FileIndex> {
        sync {
            return indexes[group] ?: emptySet()
        }
    }

    override fun getMd5(file: File): String {
        val md5File = File(file.parentFile, file.name + ".md5")
        if (md5File.exists()) {
            return md5File.readText()
        }
        throw FileNotFoundException("md5 file not found")
    }

    private fun addIndex(group: String, index: FileIndex) {
        val comparator = Collator.getInstance()
        (indexes.getOrPut(group) {
            TreeSet { a, b ->
                comparator.compare(a.name, b.name)
            }
        } as TreeSet).add(index)
    }


    final override fun updateIndexes() {
        val fileRegex = Regex("(\\w+)(\\+cu\\d+)?-.*\\.whl")
        sync {
            indexes.clear()
            val base = PipConfig.baseDir
            base.list()?.forEach { name ->
                if (!fileRegex.matches(name))
                    return@forEach
                val f = File(base, name)
                addIndex(getGroup(name), FileIndex(name, f.length().size(), f))
                val md5File = File(f.parentFile, f.name + ".md5")
                if (!md5File.exists())
                    md5File.writeText(f.inputStream().md5)
            }
            groups = indexes.keys.toList().sorted()
        }
    }

    override fun getFile(fileName: String): File = PipConfig.baseDir.resolve(fileName)

    override fun getGroup(fileName: String): String {
        val i = fileName.indexOf('-')
        return fileName.substring(0, i)
    }
}

val sizeUnits = arrayOf(" B", " KB", " MB", " GB", " TB", " PB")

fun Long.size(): String {
    var i = 0
    var size = this.toDouble()
    while (size > 1024.0) {
        size /= 1024.0
        i++
    }
    val r = "${size}000"
    return r.substring(0, r.indexOf('.') + 3) + sizeUnits[i]
}
