package com.bdadev.musicplayer.extensions

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import com.bdadev.musicplayer.util.ColorUtil
import java.lang.Exception

inline val @receiver:ColorInt Int.isColorLight
    get() = ColorUtil.isColorLight(this)

@JvmOverloads
fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int = 0): Int {
    context.theme.obtainStyledAttributes(intArrayOf(attr)).use {
        return try {
            it.getColor(0, fallback);
        } catch (e: Exception) {
            Color.BLACK
        }
    }
}

fun Context.resolveColor(@AttrRes attr: Int, fallBackColor: Int = 0) =
    resolveColor(this, attr, fallBackColor)

fun Context.surfaceColor() = resolveColor(com.google.android.material.R.attr.colorSurface, Color.WHITE)
