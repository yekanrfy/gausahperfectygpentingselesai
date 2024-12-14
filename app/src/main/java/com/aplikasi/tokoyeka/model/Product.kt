package com.aplikasi.tokoyeka.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    val imageUrl: String
)
