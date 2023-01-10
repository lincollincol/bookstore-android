package com.linc.network.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
internal annotation class ApiInstance(val type: Type)

internal enum class Type {
    BOOKS, STRIPE
}