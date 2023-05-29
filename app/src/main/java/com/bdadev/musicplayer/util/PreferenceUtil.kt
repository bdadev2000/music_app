package com.bdadev.musicplayer.util

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.bdadev.musicplayer.Constants.SONG_SORT_ORDER
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
}