package com.bdadev.musicplayer.util

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.bdadev.musicplayer.Constants.FILTER_SONG
import com.bdadev.musicplayer.Constants.KEEP_SCREEN_ON
import com.bdadev.musicplayer.Constants.LANGUAGE_NAME
import com.bdadev.musicplayer.Constants.LOCALE_AUTO_STORE_ENABLED
import com.bdadev.musicplayer.Constants.SONG_SORT_ORDER
import com.bdadev.musicplayer.Constants.TOGGLE_FULL_SCREEN
import com.bdadev.musicplayer.Constants.WHITELIST_MUSIC
import com.bdadev.musicplayer.application.MyApplication
import com.bdadev.musicplayer.extensions.getStringOrDefault
import com.bdadev.musicplayer.helper.SortOrder

object PreferenceUtil {
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())

    var songSortOrder
        get() = sharedPreferences.getStringOrDefault(
            SONG_SORT_ORDER,
            SortOrder.SongSortOrder.SONG_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(SONG_SORT_ORDER,value)
        }

    val isWhiteList : Boolean
        get() = sharedPreferences.getBoolean(WHITELIST_MUSIC,false)

    val filterLength get() = sharedPreferences.getInt(FILTER_SONG, 20)

    var languageCode: String
        get() = sharedPreferences.getString(LANGUAGE_NAME, "auto") ?: "auto"
        set(value) = sharedPreferences.edit {
            putString(LANGUAGE_NAME, value)
        }

    var isLocaleAutoStorageEnabled: Boolean
        get() = sharedPreferences.getBoolean(
            LOCALE_AUTO_STORE_ENABLED,
            false
        )
        set(value) = sharedPreferences.edit {
            putBoolean(LOCALE_AUTO_STORE_ENABLED, value)
        }

    val isFullScreenMode
        get() = sharedPreferences.getBoolean(
            TOGGLE_FULL_SCREEN, false
        )

    val isScreenOnEnabled get() = sharedPreferences.getBoolean(KEEP_SCREEN_ON, false)


}