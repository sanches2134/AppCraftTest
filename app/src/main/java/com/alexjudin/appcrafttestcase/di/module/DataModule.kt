package com.alexjudin.appcrafttestcase.di.module

import android.content.Context
import androidx.room.Room
import com.alexjudin.appcrafttestcase.data.network.AlbumApi
import com.alexjudin.appcrafttestcase.data.repository.PhotosRepositoryImpl
import com.alexjudin.appcrafttestcase.data.database.AlbumDao
import com.alexjudin.appcrafttestcase.data.database.AlbumDataBase
import com.alexjudin.appcrafttestcase.data.network.Constants.BASE_URL
import com.alexjudin.appcrafttestcase.data.network.PhotosApi
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import com.alexjudin.appcrafttestcase.data.datasource.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providePhotosRepository(
        networkPhotosDataSource: NetworkPhotosDataSource,
        networkAlbumsDataSource: NetworkAlbumsDataSource,
        localAlbumDataSource: LocalAlbumDataSource
    ): PhotosRepository =
        PhotosRepositoryImpl(networkPhotosDataSource,networkAlbumsDataSource, localAlbumDataSource)

    @Singleton
    @Provides
    fun provideNetworkPhotosDataSource(albumApiClient: AlbumApi): NetworkPhotosDataSource =
        NetworkPhotosDataSourceImpl(albumApiClient)

    @Singleton
    @Provides
    fun provideNetworkAlbumDataSource(photosApi: PhotosApi): NetworkAlbumsDataSource =
        NetworkAlbumsDataSourceImpl(photosApi)

    @Singleton
    @Provides
    fun provideLocalPhotosDataSource(newsDao: AlbumDao): LocalAlbumDataSource =
        LocalAlbumDataSourceImpl(newsDao)

    @Singleton
    @Provides
    fun providePhotosDao(context: Context): AlbumDao =
        Room.databaseBuilder(
            context.applicationContext,
            AlbumDataBase::class.java,
            "photos_db.db"
        ).build().getArticleDao()


    @Singleton
    @Provides
    fun providePhotosApiClient(): AlbumApi {


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumApiClient(): PhotosApi {


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotosApi::class.java)
    }


}