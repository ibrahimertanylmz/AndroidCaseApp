package com.oneteam.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Image(
    val id: Int,
    val url: String,
    val alt: String,
) : Parcelable