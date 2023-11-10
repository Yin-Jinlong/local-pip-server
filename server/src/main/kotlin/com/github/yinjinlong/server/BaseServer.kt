package com.github.yinjinlong.server

import com.github.yinjinlong.service.WhlFileService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

/**
 *
 * @author YJL
 */
@Controller
abstract class BaseServer : Server {

    @Autowired
    lateinit var service: WhlFileService

    fun HttpServletResponse.contentType(type: String) {
        contentType = type
    }

    fun HttpServletResponse.contentLength(length: Long) {
        setContentLengthLong(length)
    }

    fun HttpServletResponse.write(text: CharSequence) {
        outputStream.write(text.toString().toByteArray())
    }

    fun HttpServletResponse.write(bs: ByteArray) {
        outputStream.write(bs)
    }

    fun HttpServletResponse.contentHtml() {
        contentType = "text/html"
    }

    fun contentDisposition(fileName: String) = "attachment; filename=\"$fileName\""
}