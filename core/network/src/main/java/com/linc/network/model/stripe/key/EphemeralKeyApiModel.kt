package com.linc.network.model.stripe.key

data class EphemeralKeyApiModel(
    val associated_objects: List<AssociatedObject>,
    val created: Int,
    val expires: Int,
    val id: String,
    val livemode: Boolean,
    val `object`: String,
    val secret: String
)