package com.alexjudin.appcrafttestcase.data.datasource

import com.alexjudin.appcrafttestcase.data.network.PhotosApi
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import retrofit2.Response
import javax.inject.Inject

interface NetworkAlbumsDataSource  {
    suspend fun getAlbum(id: Int): Response<Array<PhotosModelItem>>
}

class NetworkAlbumsDataSourceImpl @Inject constructor(
    private val PhotosApiClient: PhotosApi
) : NetworkAlbumsDataSource {

    override suspend fun getAlbum(id: Int): Response<Array<PhotosModelItem>> =
        PhotosApiClient.getAlbum(id)

}
