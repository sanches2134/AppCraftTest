package com.alexjudin.appcrafttestcase.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
        tableName = "photo"
)

data class AlbumModel(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        val title: String,
        val userId: Int
) : Serializable