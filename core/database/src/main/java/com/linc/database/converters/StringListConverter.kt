package com.linc.database.converters

import androidx.room.TypeConverter

private const val SEPARATOR = "#"

class StringListConverter {
    @TypeConverter
    fun listToJson(value: List<String>) = value.joinToString(separator = SEPARATOR)
    @TypeConverter
    fun jsonToList(value: String): List<String> = value.split(SEPARATOR)
}