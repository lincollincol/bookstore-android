package com.linc.ui.util

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext context: Context
) {

    private val resources: Resources = context.resources

    fun getString(@StringRes id: Int): String = resources.getString(id)

    fun getString(@StringRes id: Int, vararg args: Any): String = resources.getString(id, args)

}