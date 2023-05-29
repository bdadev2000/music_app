package com.bdadev.musicplayer.extensions

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.util.ColorUtil
import com.bdadev.musicplayer.util.PreferenceUtil
import com.bdadev.musicplayer.util.VersionUtils


@Suppress("Deprecation")
fun AppCompatActivity.setLightNavigationBar(enabled: Boolean) {
    if (VersionUtils.hasOreo()) {
        val decorView = window.decorView
        var systemUiVisibility = decorView.systemUiVisibility
        systemUiVisibility = if (enabled) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        decorView.systemUiVisibility = systemUiVisibility
    }
}

fun AppCompatActivity.maybeSetScreenOn() {
    if (PreferenceUtil.isScreenOnEnabled) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

@Suppress("Deprecation")
fun AppCompatActivity.setLightStatusBar(enabled: Boolean) {
    if (VersionUtils.hasMarshmallow()) {
        val decorView = window.decorView
        val systemUiVisibility = decorView.systemUiVisibility
        if (enabled) {
            decorView.systemUiVisibility =
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility =
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}


fun AppCompatActivity.setLightStatusBarAuto(bgColor: Int) {
    setLightStatusBar(bgColor.isColorLight)
}

fun AppCompatActivity.setStatusBarColor(color: Int) {
    val statusBar = window.decorView.rootView.findViewById<View>(R.id.status_bar)
    if (statusBar != null) {
        when {
            VersionUtils.hasMarshmallow() -> statusBar.setBackgroundColor(color)
            else -> statusBar.setBackgroundColor(
                ColorUtil.darkenColor(
                    color
                )
            )
        }
    } else {
        when {
            VersionUtils.hasMarshmallow() -> window.statusBarColor = color
            else -> window.statusBarColor = ColorUtil.darkenColor(color)
        }
    }
    setLightStatusBarAuto(surfaceColor())
}


fun AppCompatActivity.setNavigationBarColorPreOreo(color: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        window.navigationBarColor = ColorUtil.darkenColor(color)
    }
}

fun AppCompatActivity.setDrawBehindSystemBars() {
    if (VersionUtils.hasOreo()) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        if (VersionUtils.hasQ()) {
            window.isNavigationBarContrastEnforced = false
        }
    } else {
        setNavigationBarColorPreOreo(surfaceColor())
        if (VersionUtils.hasMarshmallow()) {
            setStatusBarColor(Color.TRANSPARENT)
        } else {
            setStatusBarColor(Color.BLACK)
        }
    }
}

fun AppCompatActivity.setImmersiveFullscreen() {
    if (PreferenceUtil.isFullScreenMode) {
        WindowInsetsControllerCompat(window, window.decorView).apply {
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
        if (VersionUtils.hasP()) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            if (insets.displayCutout != null) {
                insets
            } else {
                // Consume insets if display doesn't have a Cutout
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}

fun AppCompatActivity.exitFullscreen() {
    WindowInsetsControllerCompat(window, window.decorView).apply {
        show(WindowInsetsCompat.Type.systemBars())
    }
}

fun AppCompatActivity.setEdgeToEdgeOrImmersive() {
    if (PreferenceUtil.isFullScreenMode) {
        setImmersiveFullscreen()
    } else {
        setDrawBehindSystemBars()
    }
}


fun AppCompatActivity.hideStatusBar() {
    hideStatusBar(PreferenceUtil.isFullScreenMode)
}

private fun AppCompatActivity.hideStatusBar(fullscreen: Boolean) {
    val statusBar = window.decorView.rootView.findViewById<View>(R.id.status_bar)
    if (statusBar != null) {
        statusBar.isGone = fullscreen
    }
}

fun AppCompatActivity.setLightNavigationBarAuto() {
    setLightNavigationBar(surfaceColor().isColorLight)
}