package com.alexjudin.appcrafttestcase.data.datasource

import androidx.lifecycle.LiveData
import com.alexjudin.appcrafttestcase.data.database.AlbumDao
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import javax.inject.Inject

interface LocalAlbumDataSource {

    suspend fun upsert(albumModel: AlbumModel): Long

    fun getSavedPhotos(): LiveData<List<AlbumModel>>

    suspend fun deletePhoto(albumModel: AlbumModel)
}

class LocalAlbumDataSourceImpl @Inject constructor(
    private val newsDao: AlbumDao
) : LocalAlbumDataSource {

    override suspend fun upsert(albumModel: AlbumModel): Long = newsDao.upsert(albumModel)

    override fun getSavedPhotos(): LiveData<List<AlbumModel>> = newsDao.getAllPhotos()

    override suspend fun deletePhoto(albumModel: AlbumModel) = newsDao.deletePhoto(albumModel)

}
