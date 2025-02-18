package com.oneteam.data.remote.model

data class ImageResponse(
    val total_results: Int,
    val page: Int,
    val per_page: Int,
    val photos: List<PexelImage>
)

data class PexelImage(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val src: Src,
    val alt: String,
)

data class Src(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)