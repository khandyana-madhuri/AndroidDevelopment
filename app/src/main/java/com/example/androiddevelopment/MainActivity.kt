package com.example.androiddevelopment

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private var boundedService: BoundedService ?= null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as BoundedService.LocalBinder
            boundedService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            boundedService = null
            isBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnTimeStamp = findViewById<Button>(R.id.timeStampBtn)
        val textTimeStamp = findViewById<TextView>(R.id.timeStampTxt)

        btnTimeStamp.setOnClickListener {
            if(isBound) {
                val timeStamp = boundedService?.getCurrentTimeStamp()
                textTimeStamp.text = timeStamp
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, BoundedService::class.java).also {
            bindService(it, connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if(isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

