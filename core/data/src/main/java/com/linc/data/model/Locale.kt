package com.linc.data.model

import com.linc.database.entity.localization.LocaleStringEntity
import com.linc.filestore.model.LocaleStringModel
import com.linc.model.AppLocale
import java.util.*

fun Locale.asExternalModel() = AppLocale(
    displayName = displayName,
    language = language,
    country = country,
)

fun LocaleStringModel.asEntity(localeId: String) = LocaleStringEntity(
    key = key,
    value = value,
    localeId = localeId
)