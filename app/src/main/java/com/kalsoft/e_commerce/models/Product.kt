package com.kalsoft.e_commerce.models

class Product(
    val id: String,
    var quantity: Int,
    val price: Double,
    var title: String,
    val url: String,
    var isFavorite: Int
)