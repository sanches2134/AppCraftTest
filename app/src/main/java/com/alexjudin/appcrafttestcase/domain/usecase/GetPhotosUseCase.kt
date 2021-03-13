package com.alexjudin.appcrafttestcase.domain.usecase

import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import retrofit2.Response
import javax.inject.Inject


class GetPhotosUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    suspend operator fun invoke(): Response<Array<AlbumModel>> =
        photosRepository.getPhotos()
}