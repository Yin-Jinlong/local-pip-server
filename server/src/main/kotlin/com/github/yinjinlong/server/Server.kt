package com.github.yinjinlong.server

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

/**
 *
 * @author YJL
 */
interface Server {

    fun home(request: HttpServletRequest, response: HttpServletResponse)

    fun get(path: String, request: HttpServletRequest, response: HttpServletResponse)

    fun download(file: String, request: HttpServletRequest, response: HttpServletResponse)

}