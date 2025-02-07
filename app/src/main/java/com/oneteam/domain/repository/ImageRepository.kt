package com.oneteam.domain.repository

import androidx.paging.PagingData
import com.oneteam.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(
        query: String
    ): Flow<PagingData<Image>>
}