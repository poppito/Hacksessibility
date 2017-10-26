package com.noni.au.app.hacksessibility.presentation.view.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.*
import com.noni.au.app.hacksessibility.R

/**
 * ideally this service will
 * get an instance of the
 * accessibility service to inspect
 * foregrounded activities.
 * @author harshoverseer
 */
class AccessibilityBindingService : Service() {

    private var mWindowManager: WindowManager? = null
    private var mFloatingIcon: View? = null

    //region lifecycle
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createFloatingWindow()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("TAG", "service started")
        //stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mWindowManager != null && mFloatingIcon != null) {
            if (mFloatingIcon?.windowToken != null) {
                mWindowManager?.removeView(mFloatingIcon!!)
            }
        }
        Log.v("TAG", "service finishing up..")
    }

    //endregion

    //region private

    private fun createFloatingWindow() {
        Log.v("TAG", "service created!")
        mFloatingIcon = LayoutInflater.from(this).inflate(R.layout.service_accessibility, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as? WindowManager

        val params = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.LEFT or Gravity.TOP
        params.x = 0
        params.y = 100

        if (mFloatingIcon != null && mWindowManager != null) {
            if (mFloatingIcon!!.windowToken == null) {
                mWindowManager!!.addView(mFloatingIcon!!, params)

                val btnClose = mFloatingIcon?.findViewById(R.id.img_close_button)
                btnClose?.setOnClickListener { stopSelf() }

                mFloatingIcon?.setOnTouchListener{ v, event ->
                    handleEvent(event, params)
                }
            }
        }
    }


    //endregion

    private fun handleEvent (event: MotionEvent, params: WindowManager.LayoutParams) : Boolean {
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f

        if (event.action == MotionEvent.ACTION_DOWN) {
            initialX = params.x
            initialY = params.y
            initialTouchX = event.getRawX()
            initialTouchY = event.getRawY()
            return true

        } else if (event.action == MotionEvent.ACTION_MOVE) {
            params.x = initialX + (event.rawX - initialTouchX).toInt()
            params.y = initialY + (event.rawY - initialTouchY).toInt()
            mWindowManager?.updateViewLayout(mFloatingIcon, params)
            return true
        }

        return false

    }
}