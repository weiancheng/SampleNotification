package com.example.weian.samplenotification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context

/**
 * Created by weian on 2017/12/13.
 *
 */

class SimpleNotification(context: Context, title: String, content: String) {
    private val notifyID = 1
    private val PLAYER_ICON = R.drawable.ic_music_player
    private val PLAYER_ICON_PLAY = R.drawable.ic_music_player_play
    private val PLAYER_ICON_STOP = R.drawable.ic_music_player_stop
    private val PLAYER_ICON_PAUSE = R.drawable.ic_music_player_pause
    private val PLAYER_ICON_FORWARD = R.drawable.ic_music_player_forward

    private var context: Context
    private var title: String
    private var content: String
    private lateinit var manager: NotificationManager
    private lateinit var notification: Notification

    init {
        this.context = context
        this.title = title
        this.content = content
    }

    private fun initNotification() {
        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification = Notification.Builder(context.applicationContext)
                .setSmallIcon(PLAYER_ICON)
                .setContentTitle(title)
                .setContentText(content)
                .addAction(PLAYER_ICON_PLAY, "Play", null)
                .addAction(PLAYER_ICON_FORWARD, "Next", null)
                .build()
    }

    /**
     * start showing the notification
     */
    fun show() {
        initNotification()
        manager.notify(notifyID, notification)
    }

    /**
     * destroy this notification
     */
    fun stop() {
    }
}