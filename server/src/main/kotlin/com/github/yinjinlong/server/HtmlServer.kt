package com.github.yinjinlong.server

import com.github.yinjinlong.config.PipConfig
import com.github.yinjinlong.html.GroupsHtmlGenerator
import com.github.yinjinlong.html.ItemHtmlGenerator
import com.github.yinjinlong.util.getLogger
import com.github.yinjinlong.util.sync
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND
import org.springframework.http.HttpHeaders
import org.springframework.scheduling.annotation.Async
import org.springframework.util.MimeTypeUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.util.Properties

/**
 *
 * @author YJL
 */
@RestController
@RequestMapping("/simple")
class HtmlServer : BaseServer() {

    val log = getLogger()

    fun HttpServletResponse.write(file: File) {
        file.inputStream().copyTo(outputStream, bufferSize = PipConfig.bufferSize)
    }

    @Async
    @GetMapping("/")
    override fun home(request: HttpServletRequest, response: HttpServletResponse) {
        log.info("get /")
        response.contentHtml()
        if (!homeHtmlFile.exists())
            GroupsHtmlGenerator.generate(service, homeHtmlFile)
        response.contentLength(homeHtmlFile.length())
        response.write(homeHtmlFile)
    }


    fun getOrGen(path: String): File? {
        sync {
            val subHtmlFile = html.resolve("$path.html")
            if (subHtmlFile.exists())
                return subHtmlFile
            if (service[path].isEmpty())
                return null
            ItemHtmlGenerator.generate(service, path, subHtmlFile)
            return subHtmlFile
        }
    }

    @Async
    @GetMapping("/{path}", "/{path}/")
    override fun get(@PathVariable path: String, request: HttpServletRequest, response: HttpServletResponse) {
        log.info("get $path")
        response.contentHtml()
        val subHtmlFile = getOrGen(path)
        if (subHtmlFile == null || !subHtmlFile.exists()) {
            response.status = SC_NOT_FOUND
            return
        }
        response.setContentLengthLong(subHtmlFile.length())
        response.write(subHtmlFile)
    }

    fun HttpServletResponse.download(file: File) {
        contentType = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE
        setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition(file.name))
        contentLength(file.length())
        write(file)
    }

    @Async
    @GetMapping("/download/{file}")
    override fun download(@PathVariable file: String, request: HttpServletRequest, response: HttpServletResponse) {
        val file = service.getFile(file)
        if (!file.exists()) {
            log.info("download 404 $file")
            response.status = SC_NOT_FOUND
        }
        log.info("download $file")
        response.download(file)
    }

    @Async
    @GetMapping("/download/{file}#md5={md5}")
    fun download(
        @PathVariable file: String,
        @PathVariable md5: String,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val file = service.getFile(file)
        if (!file.exists() || service.getMd5(file) != md5) {
            log.info("download 404 $file")
            response.status = SC_NOT_FOUND
        }
        log.info("download $file")
        response.download(file)
    }

    final val html = File("./html/")
    final val homeHtmlFile = File("./html/index.html")

    @PostConstruct
    fun init() {
        if (!html.exists())
            html.mkdirs()
    }
}