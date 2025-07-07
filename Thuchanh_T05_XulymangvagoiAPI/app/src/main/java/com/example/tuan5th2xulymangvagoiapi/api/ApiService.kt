package com.example.tuan5th2xulymangvagoiapi.api

import com.example.tuan5th2xulymangvagoiapi.data.Product
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("m1/890655-872447-default/v2/product") // Phần cuối của URL API
    suspend fun getProduct(): Response<Product>
}
