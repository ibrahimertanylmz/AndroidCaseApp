package com.oneteam.domain.use_case

import androidx.paging.PagingData
import com.oneteam.domain.model.Image
import com.oneteam.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

class GetImagesUseCase(private val imageRepository: ImageRepository) {
    fun execute(query: String): Flow<PagingData<Image>> {
        return imageRepository.getImages(query)
    }
}