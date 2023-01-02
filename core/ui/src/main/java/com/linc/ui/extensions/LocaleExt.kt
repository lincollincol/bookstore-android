package com.linc.ui.extensions

import java.util.*

val Locale.flagEmoji: String
    get() {
        val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

fun Locale?.tagsEquals(locale: Locale?, ignoreCase: Boolean = true): Boolean {
    if(this == null || locale == null) return false
    return toLanguageTag().equals(locale.toLanguageTag(), ignoreCase)
}
fun Locale?.languagesEquals(locale: Locale?, ignoreCase: Boolean = true): Boolean {
    if(this == null || locale == null) return false
    return language.equals(locale.language, ignoreCase)
}