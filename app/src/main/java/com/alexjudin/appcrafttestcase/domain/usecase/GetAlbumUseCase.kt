package com.alexjudin.appcrafttestcase.domain.usecase

import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import retrofit2.Response
import javax.inject.Inject

class GetAlbumUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    suspend operator fun invoke(id: Int): Response<Array<PhotosModelItem>> =
        photosRepository.getAlbum(id)
}