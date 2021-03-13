package com.alexjudin.appcrafttestcase.di.module


import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import com.alexjudin.appcrafttestcase.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGetPhotosUseCase(
        photosRepository: PhotosRepository
    ): GetPhotosUseCase = GetPhotosUseCase(photosRepository)

    @Singleton
    @Provides
    fun provideGetAlbumUseCase(
        photosRepository: PhotosRepository
    ): GetAlbumUseCase = GetAlbumUseCase(photosRepository)

    @Singleton
    @Provides
    fun provideGetSavedPhotosUseCase(
        photosRepository: PhotosRepository
    ): GetSavedPhotosUseCase = GetSavedPhotosUseCase(photosRepository)

    @Singleton
    @Provides
    fun provideUpsertUseCase(
        photosRepository: PhotosRepository
    ): UpsertUseCase = UpsertUseCase(photosRepository)

    @Singleton
    @Provides
    fun provideDeletePhotosUseCase(
        photosRepository: PhotosRepository
    ): DeletePhotosUseCase = DeletePhotosUseCase(photosRepository)

}