package com.alexjudin.appcrafttestcase.domain.repository

import androidx.lifecycle.LiveData
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import retrofit2.Response

interface PhotosRepository {

    suspend fun getPhotos(): Response<Array<AlbumModel>>

    suspend fun getAlbum(id:Int):Response<Array<PhotosModelItem>>

    suspend fun upsert(albumModel: AlbumModel): Long

    fun getSavedPhotos(): LiveData<List<AlbumModel>>

    suspend fun deletePhoto(albumModel: AlbumModel)
}