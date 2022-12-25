package com.linc.common.coroutines.extensions

val String.Companion.EMPTY: String get() = ""

fun String.replaceLast(oldVal: String, newVal: String): String =
    "${substringBeforeLast(oldVal)}$newVal${substringAfterLast(oldVal)}"