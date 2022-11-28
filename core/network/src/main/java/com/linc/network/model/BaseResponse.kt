package com.linc.network.model

data class BaseResponse<T>(
    val items: List<T>,
    val kind: String,
    val totalItems: Int
)