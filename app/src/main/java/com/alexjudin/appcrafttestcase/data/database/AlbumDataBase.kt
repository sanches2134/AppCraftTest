package com.alexjudin.appcrafttestcase.data.database

import androidx.room.*
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel

@Database(
    entities = [AlbumModel::class],
    version = 1,
    exportSchema = false
)
abstract class AlbumDataBase : RoomDatabase() {

    abstract fun getArticleDao(): AlbumDao

}