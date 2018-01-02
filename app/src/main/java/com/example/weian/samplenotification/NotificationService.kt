package com.example.weian.samplenotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log

/**
 * Created by weian on 2017/12/13.
 *
 */

class NotificationService : Service() {
    private val TAG = "NotificationService"

    companion object {
        private const val PLAYER_ICON = R.drawable.ic_music_player
        private const val PLAYER_ICON_PLAY = R.drawable.ic_music_player_play
        private const val PLAYER_ICON_PAUSE = R.drawable.ic_music_player_pause
        private const val PLAYER_ICON_FORWARD = R.drawable.ic_music_player_forward

        private const val ACTION_PLAY = "ACTION_PLAY"
        private const val ACTION_PAUSE = "ACTION_PAUSE"
        private const val ACTION_FORWARD = "ACTION_FORWARD"
    }

    private val notifyID = 0

    private lateinit var manager: NotificationManager
    private lateinit var notification: NotificationCompat.Builder

    private lateinit var actionPlay: NotificationCompat.Action
    private lateinit var actionPause: NotificationCompat.Action
    private lateinit var actionNext: NotificationCompat.Action

    private var musicName: String = ""

    private val mBinder = NotificationBinder()
    private var buttonEventListener: ButtonEvent ?= null

    override fun onBind(intent: Intent?): IBinder = mBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action.equals(ACTION_PLAY)) {
            musicPlayerChangeToPlay()
        } else if (intent?.action.equals(ACTION_PAUSE)) {
            musicPlayerChangeToPause()
        } else if (intent?.action.equals(ACTION_FORWARD)) {
            musicPlayerChangeToNext()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun musicPlayerChangeToPlay() {
        notification.mActions.clear()
        notification.addAction(actionPause)
        notification.addAction(actionNext)
        notification.setContentText(buttonEventListener?.onPlay())
        manager.notify(notifyID, notification.build())
    }

    private fun musicPlayerChangeToPause() {
        buttonEventListener?.onPause()
        notification.mActions.clear()
        notification.addAction(actionPlay)
        notification.addAction(actionNext)
        manager.notify(notifyID, notification.build())
    }

    private fun musicPlayerChangeToNext() {
        notification.mActions.clear()
        notification.addAction(actionPause)
        notification.addAction(actionNext)
        notification.setContentText(buttonEventListener?.onNext())
        manager.notify(notifyID, notification.build())
    }

    fun initNotification(appName: String) {
        manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // action play
        val intentPlay = Intent(this, NotificationService::class.java)
        intentPlay.setAction(ACTION_PLAY)
        val pendingIntentPlay = PendingIntent.getService(this, 0, intentPlay, 0)
        actionPlay = NotificationCompat.Action(PLAYER_ICON_PLAY, "Play", pendingIntentPlay)

        // action pause
        val intentPause = Intent(this, NotificationService::class.java)
        intentPause.setAction(ACTION_PAUSE)
        val pendingIntentPause = PendingIntent.getService(this, 0, intentPause, 0)
        actionPause = NotificationCompat.Action(PLAYER_ICON_PAUSE, "Pause", pendingIntentPause)

        // action forward
        val intentForward = Intent(this, NotificationService::class.java)
        intentForward.setAction(ACTION_FORWARD)
        val pendingIntentForward = PendingIntent.getService(this, 0, intentForward, 0)
        actionNext = NotificationCompat.Action(PLAYER_ICON_FORWARD, "Next", pendingIntentForward)

        notification = NotificationCompat.Builder(applicationContext).
                setSmallIcon(PLAYER_ICON).
                setContentTitle(appName).
                setContentText(musicName).
                addAction(actionPause).
                addAction(actionNext)
    }

    private interface IButtonEvent {
        fun onPlay(): String
        fun onPause()
        fun onNext(): String
    }

    class ButtonEventFunc {
        var onPlay: (() -> String)? = null
        var onPause: (() -> Unit)? = null
        var onNext: (() -> String)? = null
    }

    class ButtonEvent(func: ButtonEventFunc.() -> Unit) : IButtonEvent {
        private var func = ButtonEventFunc().apply(func)

        override fun onPlay(): String {
            return func.onPlay?.invoke() ?: ""
        }

        override fun onPause() {
            func.onPause?.invoke()
        }

        override fun onNext(): String {
            return func.onNext?.invoke() ?: ""
        }
    }

    /**
     * start showing the notification
     */
    fun play() {
        if (buttonEventListener == null) {
            musicPlayerChangeToPause()
        } else {
            musicPlayerChangeToPlay()
        }
    }

    fun pause() {
        musicPlayerChangeToPause()
    }

    fun removeNotification() {
        manager.cancel(notifyID)
    }

    fun setButtonEventListener(listener: ButtonEvent) {
        buttonEventListener = listener
    }

    inner class NotificationBinder : Binder() {
        fun getService() = this@NotificationService
    }
}