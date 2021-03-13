package com.alexjudin.appcrafttestcase.data.network

import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumApi {

    @GET("/albums/")
    suspend fun getPhotos(
    ): Response<Array<AlbumModel>>

}

interface PhotosApi{
    @GET("/photos/")
    suspend fun getAlbum(
        @Query("albumId")
        id: Int = 1
    ): Response<Array<PhotosModelItem>>

}