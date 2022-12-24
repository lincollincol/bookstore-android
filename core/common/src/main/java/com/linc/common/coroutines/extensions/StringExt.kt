package com.linc.common.coroutines.extensions

val String.Companion.EMPTY: String get() = ""

fun String.replaceLast(oldVal: String, newVal: String): String {
    val firstPart = substringBeforeLast(oldVal)
    val lastPart = substringAfterLast(oldVal)
    return "$firstPart$newVal$lastPart"
}