package com.alexjudin.appcrafttestcase.ui.fragments

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.presentation.service.BackgroundSoundService
import com.alexjudin.appcrafttestcase.presentation.service.MyLocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_geolocation.*


class GeolocationFragment : Fragment(R.layout.fragment_geolocation) {
    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentState: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MyLocationService.geolocation.observe(viewLifecycleOwner) {
            x.text = it.lat
            y.text = it.log
            start_geo.text = it.state
            currentState = it.state
        }

        start_geo.setOnClickListener {

            if (start_geo.text == "Геопозиция отслеживается...") {

                stopTrackingLocation()
                stopMusic()
                currentState = "Геопозиция не отслеживается..."
                start_geo.text = currentState
                x.text = " "
                y.text = " "
            } else {
                startTrackingLocation()
                startMusic()
            }
        }
    }

    private fun startMusic() {
        val svc = Intent(context, BackgroundSoundService::class.java)
        context?.startService(svc)
    }

    private fun stopMusic() {
        val svc = Intent(context, BackgroundSoundService::class.java)
        context?.stopService(svc)
    }


    private fun startTrackingLocation() {
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        Log.d("ERR", "Готово")
                        updateLocation()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                    ) {
                        Log.d("ERR", "Готово")
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Log.d("TAG", "Подтвердите пермишены")
                    }

                }).check()


    }

    private fun updateLocation() {
        buildLocationReauest()
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent())

    }

    private fun stopTrackingLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.removeLocationUpdates(getPendingIntentForStop())
    }

    private fun getPendingIntentForStop(): PendingIntent? {
        val intent = Intent(context, MyLocationService::class.java)
        intent.setAction(MyLocationService.ACCESS_PROCESS_UPDATE)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(context, MyLocationService::class.java)
        intent.setAction(MyLocationService.ACCESS_PROCESS_UPDATE)
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildLocationReauest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 2000
        locationRequest.fastestInterval = 2000
        locationRequest.smallestDisplacement = 10f
    }

}

