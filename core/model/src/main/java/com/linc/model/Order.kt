package com.linc.model

data class Order(
    val id: String,
    val targetId: String,
    val count: Int,
)