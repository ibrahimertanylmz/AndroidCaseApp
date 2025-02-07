package com.oneteam.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oneteam.data.mapping.toDomain
import com.oneteam.data.remote.service.ImageService
import com.oneteam.domain.model.Image

class ImagePagingSource(
    private val imageService: ImageService,
    private val query: String,
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val page = params.key ?: 1 // Start from page 1 if not defined
        return try {
            if (page > 5) {
                return LoadResult.Page(
                    data = emptyList(), // Empty list after 5 pages
                    prevKey = null,
                    nextKey = null
                )
            } else {
                val response = imageService.searchImages(query, perPage = params.loadSize, page = page)
                val photos = response.photos.map { it.toDomain() }

                LoadResult.Page(
                    data = photos,
                    prevKey = if (page == 1) null else page - 1, // No previous page if first page
                    nextKey = if (photos.isEmpty()) null else page + 1 // If empty, no more pages
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}