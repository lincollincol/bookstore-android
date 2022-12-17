package com.linc.network.model.book

data class BookApiModel(
    val accessInfo: AccessInfo?,
    val etag: String?,
    val id: String,
    val kind: String,
    val saleInfo: SaleInfo?,
    val searchInfo: SearchInfo?,
    val selfLink: String?,
    val volumeInfo: VolumeInfo
)