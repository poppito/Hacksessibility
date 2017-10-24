package com.noni.au.app.Hacksessibility.presentation.view.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * ideally this service will
 * get an instance of the
 * accessibility service to inspect
 * foregrounded activities.
 * @author harshoverseer
 */
class AccessibilityBindingService : Service() {

    //region lifecycle
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("TAG", "service started")
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("TAG", "service finishing up..")
    }

    //endregion
}