package com.example.androiddevelopment

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BoundedService : Service() {

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService() : BoundedService = this@BoundedService
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    fun getCurrentTimeStamp() : String {
        return "Current TimeStamp : ${System.currentTimeMillis()}"
    }
}