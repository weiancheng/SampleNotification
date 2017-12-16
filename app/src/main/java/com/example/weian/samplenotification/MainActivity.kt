package com.example.weian.samplenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var notification: SimpleNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notification = SimpleNotification(this, "Music Player", "this is my music player")
        notification.show()
    }
}
