package com.linc.network.model.stripe.customer

data class CustomerApiModel(
    val address: Any,
    val balance: Int,
    val created: Int,
    val currency: Any,
    val default_source: Any,
    val delinquent: Boolean,
    val description: Any,
    val discount: Any,
    val email: Any,
    val id: String,
    val invoice_prefix: String,
    val livemode: Boolean,
    val name: Any,
    val `object`: String,
    val phone: Any,
    val preferred_locales: List<Any>,
    val shipping: Any,
    val tax_exempt: String,
    val test_clock: Any
)