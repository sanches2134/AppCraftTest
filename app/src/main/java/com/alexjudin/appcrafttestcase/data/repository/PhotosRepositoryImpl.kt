package com.alexjudin.appcrafttestcase.data.repository

import androidx.lifecycle.LiveData
import com.alexjudin.appcrafttestcase.data.datasource.LocalAlbumDataSource
import com.alexjudin.appcrafttestcase.data.datasource.NetworkAlbumsDataSource
import com.alexjudin.appcrafttestcase.data.datasource.NetworkPhotosDataSource
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import retrofit2.Response
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val networkPhotosDataSource: NetworkPhotosDataSource,
    private val networkAlbumsDataSource: NetworkAlbumsDataSource,
    private val localAlbumDataSource: LocalAlbumDataSource
) : PhotosRepository {

    override suspend fun getPhotos(): Response<Array<AlbumModel>> =
        networkPhotosDataSource.getPhotos()

    override suspend fun getAlbum(id:Int): Response<Array<PhotosModelItem>> =
        networkAlbumsDataSource.getAlbum(id)

    override suspend fun upsert(albumModel: AlbumModel): Long = localAlbumDataSource.upsert(albumModel)

    override fun getSavedPhotos(): LiveData<List<AlbumModel>> = localAlbumDataSource.getSavedPhotos()

    override suspend fun deletePhoto(albumModel: AlbumModel) = localAlbumDataSource.deletePhoto(albumModel)


}