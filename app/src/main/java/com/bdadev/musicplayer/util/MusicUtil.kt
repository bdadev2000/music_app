package com.bdadev.musicplayer.util

import android.content.ContentUris
import android.net.Uri
import androidx.core.net.toUri
import org.koin.core.component.KoinComponent

object MusicUtil : KoinComponent {
    @JvmStatic
    fun getMediaStoreAlbumCoverUri(albumId: Long): Uri {
        val sArtworkUri = "content://media/external/audio/albumart".toUri()
        return ContentUris.withAppendedId(sArtworkUri, albumId)
    }

}