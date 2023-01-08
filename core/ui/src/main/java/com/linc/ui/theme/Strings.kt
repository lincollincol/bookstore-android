package com.linc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val MaterialTheme.strings: LocalizedStrings
    @Composable
    @ReadOnlyComposable
    get() = LocalStringProvider.current

val LocalStringProvider = staticCompositionLocalOf<LocalizedStrings> {
    error("No default strings provided")
}

data class LocalizedStrings(
    val and: String,
    val andSpaced: String,
    val cancel: String,
    val pay: String,
    val addToCart: String,
    val makeOrder: String,
    val payWithPrice: String,
    val buyWithPrice: String,
    val confirmPaymentWithPrice: String,
    val ordersWithCount: String,
    val ratingWithCount: String,
    val purchaseAllOrders: String,
    val quantityLabel: String,
    val bookPriceLabel: String,
    val totalPriceLabel: String,
    val createOrder: String,
    val bookmarks: String,
    val interests: String,
    val aboutBook: String,
    val buyNow: String,
    val addToCartWithPrice: String,
    val goToCart: String,
    val payForOrder: String,
    val share: String,
    val books: String,
    val searchBooksHint: String,
    val seeAll: String,
    val notFound: String,
    val bookNotFound: String,
    val cart: String,
    val preferences: String,
    val language: String,
    val selectedWithCount: String,
    val available: String,
) {
    companion object
}

fun LocalizedStrings.Companion.fromMap(strings: Map<String, String>) = LocalizedStrings(
    and = strings.getOrEmpty("and"),
    andSpaced = strings.getOrEmpty("andSpaced"),
    cancel = strings.getOrEmpty("cancel"),
    pay = strings.getOrEmpty("pay"),
    addToCart = strings.getOrEmpty("addToCart"),
    makeOrder = strings.getOrEmpty("makeOrder"),
    payWithPrice = strings.getOrEmpty("payWithPrice"),
    buyWithPrice = strings.getOrEmpty("buyWithPrice"),
    confirmPaymentWithPrice = "Confirm payment (%s)",
    ordersWithCount = strings.getOrEmpty("ordersWithCount"),
    ratingWithCount = strings.getOrEmpty("ratingWithCount"),
    purchaseAllOrders = strings.getOrEmpty("purchaseAllOrders"),
    quantityLabel = strings.getOrEmpty("quantityLabel"),
    bookPriceLabel = strings.getOrEmpty("bookPriceLabel"),
    totalPriceLabel = strings.getOrEmpty("totalPriceLabel"),
    createOrder = strings.getOrEmpty("createOrder"),
    bookmarks = strings.getOrEmpty("bookmarks"),
    interests = strings.getOrEmpty("interests"),
    aboutBook = strings.getOrEmpty("aboutBook"),
    buyNow = strings.getOrEmpty("buyNow"),
    addToCartWithPrice = strings.getOrEmpty("addToCartWithPrice"),
    goToCart = strings.getOrEmpty("goToCart"),
    payForOrder = strings.getOrEmpty("payForOrder"),
    share = strings.getOrEmpty("share"),
    books = strings.getOrEmpty("books"),
    searchBooksHint = strings.getOrEmpty("searchBooksHint"),
    seeAll = strings.getOrEmpty("seeAll"),
    notFound = strings.getOrEmpty("notFound"),
    bookNotFound = strings.getOrEmpty("bookNotFound"),
    cart = strings.getOrEmpty("cart"),
    preferences = strings.getOrEmpty("preferences"),
    language = strings.getOrEmpty("language"),
    selectedWithCount = "Selected %s",
    available = "Available",
)

private fun <K> Map<K, String>.getOrEmpty(key: K): String = get(key) ?: ""