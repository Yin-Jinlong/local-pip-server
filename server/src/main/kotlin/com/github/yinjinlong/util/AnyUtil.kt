package com.github.yinjinlong.util

import java.util.logging.Logger
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun Any.sync(block: () -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    synchronized(this, block)
}

fun Any.getLogger(): Logger = Logger.getLogger(this::class.simpleName)