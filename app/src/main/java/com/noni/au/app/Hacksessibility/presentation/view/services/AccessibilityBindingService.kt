package com.noni.au.app.Hacksessibility.presentation.view.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.ImageView
import com.noni.au.app.Hacksessibility.R
import com.noni.au.app.Hacksessibility.presentation.view.activities.MainActivity

/**
 * ideally this service will
 * get an instance of the
 * accessibility service to inspect
 * foregrounded activities.
 * @author harshoverseer
 */
class AccessibilityBindingService : Service() {

    private var mWindowManager: WindowManager? = null
    private var mFloatingIcon: ImageView? = null

    //region lifecycle
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("TAG", "service started")
        createNotification()
        createFloatingWindow()
        //stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWindowManager != null && mFloatingIcon != null) {
            mWindowManager?.removeView(mFloatingIcon!!)
        }
        Log.v("TAG", "service finishing up..")
    }

    //endregion

    //region private

    private fun createNotification() {
        val id = resources.getString(R.string.NOTIFICATION_CHANNEL_ID)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager


        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.img_dot_higlighted)
                .setContentTitle(resources.getString(R.string.text_channel_name))
                .setContentText(resources.getString(R.string.text_channel_description))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = resources.getString(R.string.text_channel_name)
            val description = resources.getString(R.string.text_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance)
            channel.description = description
            manager?.createNotificationChannel(channel)
        }


        val resultIntent = Intent(this, MainActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntent(resultIntent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)

        manager?.notify(R.string.NOTIFICATION_CHANNEL_ID, builder.build())
    }

    private fun createFloatingWindow() {
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        mFloatingIcon = ImageView(this)
        mFloatingIcon?.setImageResource(R.drawable.img_dot_higlighted)

        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.CENTER
        params.x = 0
        params.y = 100

        if (mFloatingIcon != null && mWindowManager != null)  {
            mWindowManager!!.addView(mFloatingIcon!!, params)
        }
    }

    //endregion
}