package com.oneteam.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oneteam.data.remote.service.ImageService
import com.oneteam.domain.model.Image
import com.oneteam.domain.repository.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val service: ImageService,
) : ImageRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getImages(
        query: String
    ): Flow<PagingData<Image>> {

        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ImagePagingSource(service, query) }
        ).flow
    }

}
