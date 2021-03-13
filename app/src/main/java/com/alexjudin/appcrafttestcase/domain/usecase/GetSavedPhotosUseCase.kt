package com.alexjudin.appcrafttestcase.domain.usecase

import androidx.lifecycle.LiveData
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import javax.inject.Inject


class GetSavedPhotosUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    operator fun invoke(): LiveData<List<AlbumModel>> =
        photosRepository.getSavedPhotos()
}