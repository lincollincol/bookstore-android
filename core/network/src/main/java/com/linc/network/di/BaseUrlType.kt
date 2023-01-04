package com.linc.network.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrlType(val url: Url)

enum class Url {
    BOOKS_API, STRIPE_API
}