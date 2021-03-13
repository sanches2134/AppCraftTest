package com.alexjudin.appcrafttestcase.presentation.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


class BackgroundSoundService : Service() {
    var player: MediaPlayer? = null

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, com.alexjudin.appcrafttestcase.R.raw.music)
        player!!.isLooping = true
        player!!.setVolume(100f, 100f)
    }

    @SuppressLint("WrongConstant")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player!!.start()
        return 1
    }

    override fun onDestroy() {
        player!!.stop()
        player!!.release()
    }

}