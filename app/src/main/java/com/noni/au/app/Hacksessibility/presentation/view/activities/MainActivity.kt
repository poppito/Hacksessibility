package com.noni.au.app.Hacksessibility.presentation.view.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.noni.au.app.Hacksessibility.R
import com.noni.au.app.Hacksessibility.app.Hacksessibility
import com.noni.au.app.Hacksessibility.injection.ActivityModule
import com.noni.au.app.Hacksessibility.injection.DaggerActivityComponent
import com.noni.au.app.Hacksessibility.presentation.presenters.MainPresenter
import com.noni.au.app.Hacksessibility.presentation.presenters.MainPresenter.ViewSurface
import com.noni.au.app.Hacksessibility.presentation.view.services.AccessibilityBindingService
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewSurface {

    @Inject
    lateinit var mPresenter: MainPresenter

    private val PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        mPresenter.onStart(this)
        checkIfPermissionHasBeenGranted()
    }

    private fun inject() {
        val app = application as Hacksessibility
        DaggerActivityComponent.builder()
                .appComponent(app.mAppComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERMISSION_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startService(Intent(this, AccessibilityBindingService::class.java))
            } else {
                val sb = Snackbar.make(txt_view, "nope", Snackbar.LENGTH_SHORT)
                sb.show()
            }
        } else {
            val sb = Snackbar.make(txt_view, "nope", Snackbar.LENGTH_SHORT)
            sb.show()
        }
    }


    //region private

    private fun checkIfPermissionHasBeenGranted() {
        if (!Settings.canDrawOverlays(this)) {
            val localIntent = Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION")
            localIntent.data = Uri.parse("package:" + packageName)
            localIntent.flags = 268435456
            startActivity(localIntent)
            startActivityForResult(localIntent, PERMISSION_CODE)
         } else {
            startService(Intent(this, AccessibilityBindingService::class.java))
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.SYSTEM_ALERT_WINDOW), PERMISSION_CODE)
        } else {
            startService(Intent(this, AccessibilityBindingService::class.java))
        }*/
    }


    //endregion
}
