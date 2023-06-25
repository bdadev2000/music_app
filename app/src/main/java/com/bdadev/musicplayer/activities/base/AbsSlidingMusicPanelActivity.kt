package com.bdadev.musicplayer.activities.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import com.bdadev.musicplayer.activities.PermissionActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

abstract class AbsSlidingMusicPanelActivity : AbsMusicServiceActivity(), SharedPreferences.OnSharedPreferenceChangeListener{
    companion object {
        val TAG: String = AbsSlidingMusicPanelActivity::class.java.simpleName
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val panelState: Int
        get() = bottomSheetBehavior.state
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Handle back press ${bottomSheetBehavior.state}")
            if (!handleBackPress()) {
                remove()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermissions()) {
            startActivity(Intent(this, PermissionActivity::class.java))
            finish()
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

    private fun handleBackPress(): Boolean {
        if (panelState == BottomSheetBehavior.STATE_EXPANDED) {
            collapsePanel()
            return true
        }
        return false
    }

    fun collapsePanel() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}