package com.linc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val MaterialTheme.strings: LocalizedStrings
    @Composable
    @ReadOnlyComposable
    get() = LocalStringProvider.current

val LocalStringProvider = staticCompositionLocalOf { englishLocaleStrings }

data class LocalizedStrings(
    val and: String,
    val andSpaced: String,
    val cancel: String,
    val pay: String,
    val addToCart: String,
    val makeOrder: String,
    val payWithPrice: String,
    val buyWithPrice: String,
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
)

val englishLocaleStrings = LocalizedStrings(
    and = "and",
    andSpaced = " and ",
    cancel = "Cancel",
    pay = "Pay",
    addToCart = "Add to cart",
    makeOrder = "Make order",
    payWithPrice = "Pay %s",
    buyWithPrice = "Buy (%s)",
    ordersWithCount = "Orders (%d)",
    ratingWithCount = "%.2f (%d)",
    purchaseAllOrders = "Purchase all",
    quantityLabel = "Quantity:",
    bookPriceLabel = "Book price:",
    totalPriceLabel = "Total price:",
    createOrder = "Create order",
    bookmarks = "Bookmarks",
    interests = "Interests",
    aboutBook = "About book",
    buyNow = "Buy now",
    addToCartWithPrice = "Add to cart (%s)",
    goToCart = "Go to cart",
    payForOrder = "Pay for order",
    share = "Share",
    books = "Books",
    searchBooksHint = "Search books by title, author, ISBN …",
    seeAll = "See all",
    notFound = "Not found!",
    bookNotFound = "Book not found!",
    cart = "Cart",
    preferences = "Preferences",
    language = "Language"
)

val ukrainianLocaleStrings = LocalizedStrings(
    and = "та",
    andSpaced = " та ",
    cancel = "Скасувати",
    pay = "Оплатити",
    addToCart = "Додати в кошик",
    makeOrder = "Зробити замовлення",
    payWithPrice = "Оплатити %s",
    buyWithPrice = "Купити (%s)",
    ordersWithCount = "Замовлення (%d)",
    ratingWithCount = "%.2f (%d)",
    purchaseAllOrders = "Купити все",
    quantityLabel = "Кількість:",
    bookPriceLabel = "Вартість книги:",
    totalPriceLabel = "Загальна вартість:",
    createOrder = "Створити замовлення",
    bookmarks = "Закладки",
    interests = "інтереси",
    aboutBook = "Про книгу",
    buyNow = "Купити зараз",
    addToCartWithPrice = "Додати в кошик (%s)",
    goToCart = "Перейти до кошика",
    payForOrder = "Оплатити замовлення",
    share = "Поширити",
    books = "Книги",
    searchBooksHint = "Пошук книги за назвою, автором, ISBN …",
    seeAll = "Переглянути все",
    notFound = "Не знайдено!",
    bookNotFound = "Книгу не знайдено!",
    cart = "Кошик",
    preferences = "Уподобання",
    language = "Мова"
)