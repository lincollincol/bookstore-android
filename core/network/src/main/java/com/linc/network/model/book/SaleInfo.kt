package com.linc.network.model.book

data class SaleInfo(
    val country: String,
    val saleability: Saleability,
    val isEbook: Boolean,
    val listPrice: ListPrice,
    val retailPrice: RetailPrice
)