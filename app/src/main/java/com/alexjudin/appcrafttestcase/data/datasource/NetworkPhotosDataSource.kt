package com.alexjudin.appcrafttestcase.data.datasource

import com.alexjudin.appcrafttestcase.data.network.AlbumApi
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import retrofit2.Response
import javax.inject.Inject

interface NetworkPhotosDataSource {
    suspend fun getPhotos(): Response<Array<AlbumModel>>
}

class NetworkPhotosDataSourceImpl @Inject constructor(
    private val AlbumApiClient: AlbumApi
) : NetworkPhotosDataSource {

    override suspend fun getPhotos(): Response<Array<AlbumModel>> =
        AlbumApiClient.getPhotos()


}
