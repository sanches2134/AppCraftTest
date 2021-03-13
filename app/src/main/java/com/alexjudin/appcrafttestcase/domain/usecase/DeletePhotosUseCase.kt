package com.alexjudin.appcrafttestcase.domain.usecase

import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import javax.inject.Inject

class DeletePhotosUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    suspend operator fun invoke(albumModel: AlbumModel) =
        photosRepository.deletePhoto(albumModel)
}