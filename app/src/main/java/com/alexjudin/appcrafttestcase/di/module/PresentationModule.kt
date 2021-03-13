package com.alexjudin.appcrafttestcase.di.module

import android.content.Context
import com.alexjudin.appcrafttestcase.presentation.viewmodel.AlbumViewModel
import com.alexjudin.appcrafttestcase.domain.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PresentationModule {

    @Singleton
    @Provides
    fun providePhotosViewModel(
        getPhotosUseCase: GetPhotosUseCase,
        getAlbumUseCase: GetAlbumUseCase,
        deletePhotosUseCase: DeletePhotosUseCase,
        getSavedPhotosUseCase: GetSavedPhotosUseCase,
        upsertUseCase: UpsertUseCase,
        context: Context
    ): AlbumViewModel = AlbumViewModel(
        getPhotosUseCase,
        getAlbumUseCase,
        deletePhotosUseCase,
        getSavedPhotosUseCase,
        upsertUseCase,
        context
    )
}
