package com.linc.common.coroutines.extensions

import java.text.SimpleDateFormat

const val DD_MM_YYYY: String = "dd/MM/yyyy"

val String.Companion.EMPTY: String get() = ""

fun String.replaceLast(oldVal: String, newVal: String): String =
    "${substringBeforeLast(oldVal)}$newVal${substringAfterLast(oldVal)}"