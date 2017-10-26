package com.noni.au.app.hacksessibility.presentation.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.noni.au.app.hacksessibility.R
import com.noni.au.app.hacksessibility.app.Hacksessibility
import com.noni.au.app.hacksessibility.injection.ActivityModule
import com.noni.au.app.hacksessibility.injection.DaggerActivityComponent
import com.noni.au.app.hacksessibility.presentation.presenters.MainPresenter
import com.noni.au.app.hacksessibility.presentation.presenters.MainPresenter.ViewSurface
import com.noni.au.app.hacksessibility.presentation.view.services.AccessibilityBindingService
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
        txt_view.text = "test"
    }

    private fun inject() {
        val app = application as Hacksessibility
        DaggerActivityComponent.builder()
                .appComponent(app.mAppComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                startService(Intent(this, AccessibilityBindingService::class.java))
            } else {
                askForPermission()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.canDrawOverlays(this)) {
                    startService(Intent(this, AccessibilityBindingService::class.java))
                    finish()
                } else {
                    val sb = Snackbar.make(txt_view, "nope", Snackbar.LENGTH_SHORT)
                    sb.show()
                }
            }
        } else {
            val sb = Snackbar.make(txt_view, "nope", Snackbar.LENGTH_SHORT)
            sb.show()
        }
    }


    //region private

    private fun askForPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
                val localIntent = Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION")
                localIntent.data = Uri.parse("package:" + packageName)
                localIntent.flags = 268435456
                startActivity(localIntent)
                startActivityForResult(localIntent, PERMISSION_CODE)
            }
    }


    //endregion
}
