package com.linc.network.model.book

data class SaleInfo(
    val country: String,
    val listPrice: ListPrice,
    val retailPrice: RetailPrice
)