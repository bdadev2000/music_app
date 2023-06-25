package com.bdadev.musicplayer

import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.ALBUM_ARTIST

object Constants {
    const val DATA = "_data"

    const val SONG_SORT_ORDER = "song_sort_order"
    const val WHITELIST_MUSIC = "whitelist_music"
    const val FILTER_SONG = "filter_song"
    const val LANGUAGE_NAME = "language_name"
    const val LOCALE_AUTO_STORE_ENABLED = "locale_auto_store_enabled"
    const val TOGGLE_FULL_SCREEN = "toggle_full_screen"
    const val KEEP_SCREEN_ON = "keep_screen_on"

    const val IS_MUSIC =
        MediaStore.Audio.AudioColumns.IS_MUSIC + "=1" + " AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''"


    @Suppress("Deprecation")
    val baseProjection = arrayOf(
        BaseColumns._ID, // 0
        MediaStore.Audio.AudioColumns.TITLE, // 1
        MediaStore.Audio.AudioColumns.TRACK, // 2
        MediaStore.Audio.AudioColumns.YEAR, // 3
        MediaStore.Audio.AudioColumns.DURATION, // 4
        DATA, // 5
        MediaStore.Audio.AudioColumns.DATE_MODIFIED, // 6
        MediaStore.Audio.AudioColumns.ALBUM_ID, // 7
        MediaStore.Audio.AudioColumns.ALBUM, // 8
        MediaStore.Audio.AudioColumns.ARTIST_ID, // 9
        MediaStore.Audio.AudioColumns.ARTIST, // 10
        MediaStore.Audio.AudioColumns.COMPOSER, // 11
        ALBUM_ARTIST // 12
    )
}