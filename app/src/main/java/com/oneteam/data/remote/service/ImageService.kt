package com.oneteam.data.remote.service

import com.oneteam.data.remote.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {
    @GET("search")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 50,
        @Query("page") page: Int = 1
    ): ImageResponse
}