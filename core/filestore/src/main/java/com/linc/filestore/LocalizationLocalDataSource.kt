package com.linc.filestore

import android.content.res.AssetManager
import com.google.gson.Gson
import com.linc.common.coroutines.extensions.readAssetsFile
import com.linc.filestore.model.LocaleModel
import com.linc.filestore.model.LocaleStringModel
import com.linc.filestore.model.LocaleStringsModel
import java.util.*
import javax.inject.Inject

private const val DEFAULT_LOCALE = "en-EN"
private const val LOCALES_FILE = "locales.json"
private const val LOCALES_DIR = "locales/"

class LocalizationLocalDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) {

    fun getAvailableLocales(): List<Locale> = readAssetFile<LocaleModel>(LOCALES_FILE)
        .locales
        .map { it.toLocale() }

    fun getLocalesVersion(): Int = readAssetFile<LocaleModel>(LOCALES_FILE).version

    fun getDefaultLocale(): Locale = DEFAULT_LOCALE.toLocale()

    fun getLocale(code: String): List<LocaleStringModel> =
        readAssetFile<LocaleStringsModel>(getLocaleFile(code)).strings

    private fun getLocaleFile(code: String): String =
        String.format("%s%s.json", LOCALES_DIR, code)

    private inline fun <reified T> readAssetFile(filename: String): T {
        return gson.fromJson(assetManager.readAssetsFile(filename), T::class.java)
    }

    private fun String.toLocale(): Locale =
        split("-").let { Locale(it.first(), it.last()) }
}