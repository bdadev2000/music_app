package com.bdadev.musicplayer.helper

import android.provider.MediaStore

class SortOrder {

    interface SongSortOrder{
        companion object{
            const val SONG_A_Z = MediaStore.Audio.Media.TITLE

        }
    }
}