package com.example.tuan5th2xulymangvagoiapi.data

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double,
    @SerializedName("imgURL") val imageUrl: String,
    @SerializedName("des") val description: String
    // Thêm các trường khác nếu API trả về
)