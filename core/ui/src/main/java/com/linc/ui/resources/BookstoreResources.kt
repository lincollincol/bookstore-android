package com.linc.ui.resources

import com.linc.ui.icon.BookstoreIcons

object BookstoreResources {
    val icons: BookstoreIcons = BookstoreIcons
    val strings: BookstoreIcons = BookstoreIcons
}

object BookstoreStrings {

    private var strings: Map<String, String> = mapOf()
    private fun string(key: String): String = strings[key] ?: key

    fun setStrings(strings: Map<String, String>) {
        this.strings = strings
    }

    val And: String get() = string("And")
    val AndSpaced: String = string("andSpaced")
    val Cancel: String = string("cancel")
    val Pay: String = string("pay")
    val AddToCart: String = string("addToCart")
    val MakeOrder: String = string("makeOrder")
    val PayWithPrice: String = string("payWithPrice")
    val BuyWithPrice: String = string("buyWithPrice")
    val OrdersWithCount: String = string("ordersWithCount")
    val RatingWithCount: String = string("ratingWithCount")
    val PurchaseAllOrders: String = string("purchaseAllOrders")
    val QuantityLabel: String = string("quantityLabel")
    val BookPriceLabel: String = string("bookPriceLabel")
    val TotalPriceLabel: String = string("totalPriceLabel")
    val CreateOrder: String = string("createOrder")
    val Bookmarks: String = string("bookmarks")
    val Interests: String = string("interests")
    val AboutBook: String = string("aboutBook")
    val BuyNow: String = string("buyNow")
    val AddToCartWithPrice: String = string("addToCartWithPrice")
    val GoToCart: String = string("goToCart")
    val PayForOrder: String = string("payForOrder")
    val Share: String = string("share")
    val Books: String = string("books")
    val SearchBooksHint: String = string("searchBooksHint")
    val SeeAll: String = string("seeAll")
    val NotFound: String = string("notFound")
    val BookNotFound: String = string("bookNotFound")
    val Cart: String = string("cart")
    val Preferences: String get() = string("preferences")
    val Language: String get() = string("language")
}