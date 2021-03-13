package com.alexjudin.appcrafttestcase.presentation.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexjudin.appcrafttestcase.data.network.Resource
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import com.alexjudin.appcrafttestcase.domain.usecase.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val getAlbumUseCase: GetAlbumUseCase,
    private val deletePhotosUseCase: DeletePhotosUseCase,
    private val getSavedPhotosUseCase: GetSavedPhotosUseCase,
    private val upsertUseCase: UpsertUseCase,
    private val context: Context
) : ViewModel() {

    val albums: MutableLiveData<Resource<Array<AlbumModel>>> = MutableLiveData()
    private var albumResponse: Array<AlbumModel>? = null

    val photos: MutableLiveData<Resource<Array<PhotosModelItem>>> = MutableLiveData()
    private var photosResponse: Array<PhotosModelItem>? = null

    init {

        getAlbum()
    }

    fun getAlbum() = viewModelScope.launch {
        safeAlbumCall()
    }

    fun getPhotos(id: Int) = viewModelScope.launch {
        safePhotosCall(id)
    }

    private fun handlePhotosResponse(newsResponse: retrofit2.Response<Array<AlbumModel>>): Resource<Array<AlbumModel>> {
        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                    this.albumResponse = resultResponse


                return Resource.Success(this.albumResponse ?: resultResponse)
            }
        }
        return Resource.Error(newsResponse.message())
    }

    private fun handleAlbumResponse(newsResponse: retrofit2.Response<Array<PhotosModelItem>>): Resource<Array<PhotosModelItem>> {
        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { resultResponse ->
                this.photosResponse = resultResponse


                return Resource.Success(this.photosResponse ?: resultResponse)
            }
        }
        return Resource.Error(newsResponse.message())
    }

    fun saveAlbum(albumModel: AlbumModel) = viewModelScope.launch {
        upsertUseCase(albumModel)
    }

    fun getSavedNews() = getSavedPhotosUseCase()

    fun deleteArticle(albumModel: AlbumModel) = viewModelScope.launch {
        deletePhotosUseCase(albumModel)
    }

    private suspend fun safeAlbumCall() {
        albums.postValue(Resource.Loading())
        try {
            if (checkInternet()) {
                val response = getPhotosUseCase()
                albums.postValue(handlePhotosResponse(response))
            } else {
                albums.postValue(Resource.Error("Нет подключения к интернету!("))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> albums.postValue(Resource.Error("Ошибка соединения..."))
                else -> {albums.postValue(Resource.Error("Ошибка преобразования..."))
                Log.d("TAG",t.message.toString())}

            }

        }
    }

    private suspend fun safePhotosCall(id:Int) {
        photos.postValue(Resource.Loading())
        try {
            if (checkInternet()) {
                val response = getAlbumUseCase(id)
                photos.postValue(handleAlbumResponse(response))
            } else {
                photos.postValue(Resource.Error("Нет подключения к интернету!("))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> albums.postValue(Resource.Error("Ошибка соединения..."))
                else -> {albums.postValue(Resource.Error("Ошибка преобразования..."))
                    Log.d("TAG",t.message.toString())}

            }

        }
    }



    private fun checkInternet(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= 23) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capability =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capability.hasTransport(TRANSPORT_WIFI) -> true
                capability.hasTransport(TRANSPORT_ETHERNET) -> true
                capability.hasTransport(TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        return false
    }
}