package com.example.weian.samplenotification

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews

/**
 * Created by weian on 2018/1/10.
 *
 */

class NotificationService2: Service() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: Notification
    private val binder = NotificationBinder()

    companion object {
        private const val NOTIFY_ID = 0
    }

    fun initNotification() {
        val views = RemoteViews(packageName, R.layout.notification)
        views.setImageViewResource(R.id.iv_album, R.drawable.test_album)
        views.setImageViewResource(R.id.btn_next, R.drawable.ic_music_player_forward)
        views.setImageViewResource(R.id.btn_play, R.drawable.ic_music_player_play)
        views.setTextViewText(R.id.tv_music_name, "this is music name")
        views.setTextViewText(R.id.tv_singer, "this is singer")
        views.setTextViewText(R.id.tv_now_time, "000:000")

        val bigViews = RemoteViews(packageName, R.layout.big_notification)
        bigViews.setImageViewResource(R.id.iv_big_album, R.drawable.test_album)
        bigViews.setImageViewResource(R.id.btn_big_next, R.drawable.ic_music_player_forward)
        bigViews.setImageViewResource(R.id.btn_big_play, R.drawable.ic_music_player_play)
        bigViews.setTextViewText(R.id.tv_big_music_name, "this is big music name")
        bigViews.setTextViewText(R.id.tv_big_singer, "this is big singer name")
        bigViews.setTextViewText(R.id.tv_big_now_time, "00000:00000")
        bigViews.setTextViewText(R.id.tv_big_duration, "00000:00000")

        notification = NotificationCompat.Builder(this).
                setSmallIcon(R.drawable.ic_music_player).
                setOngoing(true).
                //setContent(views).
                build()
        notification.contentView = views
        notification.bigContentView = bigViews
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFY_ID, notification)
    }

    fun removeNotification() {
        notificationManager.cancel(NOTIFY_ID)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    inner class NotificationBinder : Binder() {
        fun getService() = this@NotificationService2
    }
}