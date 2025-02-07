package com.oneteam.data.mapping

import com.oneteam.data.remote.model.PexelImage
import com.oneteam.domain.model.Image

fun PexelImage.toDomain(): Image {
    return Image(
        id = id,
        url = src.small,
        alt = alt
    )
}