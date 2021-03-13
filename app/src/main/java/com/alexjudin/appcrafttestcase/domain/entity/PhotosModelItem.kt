package com.alexjudin.appcrafttestcase.domain.entity

import java.io.Serializable

data class PhotosModelItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
):Serializable