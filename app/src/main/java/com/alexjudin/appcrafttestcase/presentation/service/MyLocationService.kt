package com.alexjudin.appcrafttestcase.presentation.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.alexjudin.appcrafttestcase.domain.entity.ConnectionModel
import com.google.android.gms.location.LocationResult
import java.lang.StringBuilder

class MyLocationService:BroadcastReceiver() {



    companion object{
        val ACCESS_PROCESS_UPDATE="com.alexjudin.appcrafttestcase.presentation.service.UPDATE_LOCATION"

        var geolocation: MutableLiveData<ConnectionModel> = MutableLiveData()
    }
    override fun onReceive(context: Context?, intent: Intent?) {


        if(intent!=null)
        {
            val action=intent!!.action
            if (action.equals(ACCESS_PROCESS_UPDATE)){

                val result=LocationResult.extractResult(intent!!)
                if (result!=null) {

                    val location=result.lastLocation
                    val locationString=StringBuilder(location.latitude.toString()).append("/").append(location.longitude).toString()
                    try {
                        Log.d("TAG",locationString)
                        val connectionModel= ConnectionModel(
                                location.latitude.toString(),
                                location.longitude.toString(),
                                "Геопозиция отслеживается..."
                        )
                        geolocation.postValue(connectionModel)
                    }
                    catch (e:Exception){
                    }
                }
                else
                {
                    val connectionModel= ConnectionModel(
                           " ",
                            " ",
                            "Геопозиция не отслеживается..."
                    )
                    geolocation.postValue(connectionModel)
                }
            }

        }

    }


}