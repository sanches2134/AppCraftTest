package com.alexjudin.appcrafttestcase.domain.usecase

import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.repository.PhotosRepository
import javax.inject.Inject

class UpsertUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    suspend operator fun invoke(albumModel: AlbumModel): Long =
        photosRepository.upsert(albumModel)
}