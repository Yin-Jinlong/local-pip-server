@file:Suppress("unused")

package com.github.yinjinlong.security

import java.io.InputStream
import java.security.MessageDigest
import java.security.MessageDigest.getInstance

/**
 * 消息摘要
 *
 * @author YJL
 */
enum class MD(algorithm: String) {

    MD2("MD2"),
    MD5("MD5"),

    SHA1("SHA-1"),
    SHA1_224("SHA-224"),
    SHA1_256("SHA-256"),
    SHA1_384("SHA-384"),
    SHA1_512("SHA-512"),

    SHA2_512_224("SHA-512/224"),
    SHA2_512_256("SHA-512/256"),

    SHA3_224("SHA3-224"),
    SHA3_256("SHA3-256"),
    SHA3_384("SHA3-384"),
    SHA3_512("SHA3-512");

    private val INSTANCE: MessageDigest = getInstance(algorithm)

    fun gen(msg: String) = gen(msg.toByteArray())

    fun gen(data: ByteArray): String = INSTANCE.digest(data).hex

    fun gen(ins: InputStream): String {
        val buf = ByteArray(4 * 1024)
        var l = ins.read(buf)
        while (l != -1) {
            INSTANCE.update(buf, 0, l)
            l = ins.read(buf)
        }
        ins.close()
        return INSTANCE.digest().hex
    }

}

internal val ByteArray.hex: String
    get() {
        val s = StringBuilder()
        for (b in this) {
            val v = b.toInt()
            s.append((v shr 4).hex)
            s.append(v.hex)
        }
        return s.toString()
    }

internal val Int.hex
    get() = "0123456789abcdef"[toInt() and 0xf]

internal val Int.HEX
    get() = "0123456789ABCDEF"[toInt() and 0xf]

val String.md2
    get() = MD.MD2.gen(this)

val String.md5
    get() = MD.MD5.gen(this)

val String.sha1
    get() = MD.SHA1.gen(this)

val String.sha1_224
    get() = MD.SHA1_224.gen(this)

val String.sha1_256
    get() = MD.SHA1_256.gen(this)

val String.sha1_384
    get() = MD.SHA1_384.gen(this)

val String.sha1_512
    get() = MD.SHA1_512.gen(this)

val String.sha2_512_224
    get() = MD.SHA2_512_224.gen(this)

val String.sha2_512_256
    get() = MD.SHA2_512_256.gen(this)

val String.sha3_224
    get() = MD.SHA3_224.gen(this)

val String.sha3_256
    get() = MD.SHA3_256.gen(this)

val String.sha3_384
    get() = MD.SHA3_384.gen(this)

val String.sha3_512
    get() = MD.SHA3_512.gen(this)


val ByteArray.md2
    get() = MD.MD2.gen(this)

val ByteArray.md5
    get() = MD.MD5.gen(this)

val ByteArray.sha1
    get() = MD.SHA1.gen(this)

val ByteArray.sha1_224
    get() = MD.SHA1_224.gen(this)

val ByteArray.sha1_256
    get() = MD.SHA1_256.gen(this)

val ByteArray.sha1_384
    get() = MD.SHA1_384.gen(this)

val ByteArray.sha1_512
    get() = MD.SHA1_512.gen(this)

val ByteArray.sha2_512_224
    get() = MD.SHA2_512_224.gen(this)

val ByteArray.sha2_512_256
    get() = MD.SHA2_512_256.gen(this)

val ByteArray.sha3_224
    get() = MD.SHA3_224.gen(this)

val ByteArray.sha3_256
    get() = MD.SHA3_256.gen(this)

val ByteArray.sha3_384
    get() = MD.SHA3_384.gen(this)

val ByteArray.sha3_512
    get() = MD.SHA3_512.gen(this)


val InputStream.md2
    get() = MD.MD2.gen(this)

val InputStream.md5
    get() = MD.MD5.gen(this)

val InputStream.sha1
    get() = MD.SHA1.gen(this)

val InputStream.sha1_224
    get() = MD.SHA1_224.gen(this)

val InputStream.sha1_256
    get() = MD.SHA1_256.gen(this)

val InputStream.sha1_384
    get() = MD.SHA1_384.gen(this)

val InputStream.sha1_512
    get() = MD.SHA1_512.gen(this)

val InputStream.sha2_512_224
    get() = MD.SHA2_512_224.gen(this)

val InputStream.sha2_512_256
    get() = MD.SHA2_512_256.gen(this)

val InputStream.sha3_224
    get() = MD.SHA3_224.gen(this)

val InputStream.sha3_256
    get() = MD.SHA3_256.gen(this)

val InputStream.sha3_384
    get() = MD.SHA3_384.gen(this)

val InputStream.sha3_512
    get() = MD.SHA3_512.gen(this)