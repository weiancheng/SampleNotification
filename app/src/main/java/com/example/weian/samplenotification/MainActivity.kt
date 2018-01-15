package com.example.weian.samplenotification

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class MainActivity : AppCompatActivity() {

    /*private var notificationService: NotificationService ?= null
    private val TAG = "SampleNotification"

    var buttonEvent = NotificationService.ButtonEvent {
        onPlay = {
            Log.i(TAG, "onPlay")
            "music name"
        }

        onPause = {
            Log.i(TAG, "onPause")
        }

        onNext = {
            Log.i(TAG, "onNext")
            "music name 2"
        }
    }

    private val mConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            notificationService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            notificationService = (service as NotificationService.NotificationBinder).getService()
            notificationService?.initNotification("SampleNotification")
            notificationService?.setButtonEventListener(buttonEvent)
            notificationService?.play()
        }
    }*/

    private var notificationService: NotificationService2? = null

    private val mConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            notificationService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            notificationService = (service as NotificationService2.NotificationBinder).getService()
            notificationService?.initNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, NotificationService2::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationService?.removeNotification()
        unbindService(mConnection)
    }
}
