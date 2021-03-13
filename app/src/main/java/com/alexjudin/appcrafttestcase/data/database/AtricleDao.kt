package com.alexjudin.appcrafttestcase.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(albumModel: AlbumModel): Long

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): LiveData<List<AlbumModel>>

    @Delete
    suspend fun deletePhoto(albumModel: AlbumModel)
}