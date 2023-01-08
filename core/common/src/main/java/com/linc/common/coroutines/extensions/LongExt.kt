package com.linc.common.coroutines.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.format(pattern: String) = SimpleDateFormat(pattern, Locale.US).format(this)
