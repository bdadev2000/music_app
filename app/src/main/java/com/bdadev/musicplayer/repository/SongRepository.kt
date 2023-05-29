package com.bdadev.musicplayer.repository

import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import com.bdadev.musicplayer.Constants
import com.bdadev.musicplayer.Constants.IS_MUSIC
import com.bdadev.musicplayer.Constants.baseProjection
import com.bdadev.musicplayer.extensions.getInt
import com.bdadev.musicplayer.extensions.getLong
import com.bdadev.musicplayer.extensions.getString
import com.bdadev.musicplayer.extensions.getStringOrNull
import com.bdadev.musicplayer.helper.SortOrder
import com.bdadev.musicplayer.model.Song
import com.bdadev.musicplayer.util.PreferenceUtil
import com.bdadev.musicplayer.util.VersionUtils
import java.text.Collator

interface SongRepository{
    fun songs() : List<Song>

    fun songs(cursor: Cursor?) : List<Song>
    fun sortedSongs(cursor: Cursor?) : List<Song>
}

class RealSongRepository(private val context: Context) : SongRepository{
    override fun songs(): List<Song> {
        return sortedSongs(makeSongCursor(null, null))
    }

    override fun songs(cursor: Cursor?): List<Song> {
        val songs = arrayListOf<Song>()
        if(cursor != null && cursor.moveToFirst()){
            do {
                songs.add(getSongFromCursorImpl(cursor))
            }while (cursor.moveToNext())
        }
        cursor?.close()
        return songs
    }

    override fun sortedSongs(cursor: Cursor?): List<Song> {
        val collator = Collator.getInstance()
        val songs =  songs(cursor)
        return when(PreferenceUtil.songSortOrder){
            SortOrder.SongSortOrder.SONG_A_Z -> {
                songs.sortedWith{s1,s2-> collator.compare(s1.title,s2.title)}
            }
            else -> songs
        }
    }

    private fun getSongFromCursorImpl(
        cursor: Cursor
    ): Song {
        val id = cursor.getLong(MediaStore.Audio.AudioColumns._ID)
        val title = cursor.getString(MediaStore.Audio.AudioColumns.TITLE)
        val trackNumber = cursor.getInt(MediaStore.Audio.AudioColumns.TRACK)
        val year = cursor.getInt(MediaStore.Audio.AudioColumns.YEAR)
        val duration = cursor.getLong(MediaStore.Audio.AudioColumns.DURATION)
        val data = cursor.getString(Constants.DATA)
        val dateModified = cursor.getLong(MediaStore.Audio.AudioColumns.DATE_MODIFIED)
        val albumId = cursor.getLong(MediaStore.Audio.AudioColumns.ALBUM_ID)
        val albumName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM)
        val artistId = cursor.getLong(MediaStore.Audio.AudioColumns.ARTIST_ID)
        val artistName = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST)
        val composer = cursor.getStringOrNull(MediaStore.Audio.AudioColumns.COMPOSER)
        val albumArtist = cursor.getStringOrNull("album_artist")
        return Song(
            id,
            title,
            trackNumber,
            year,
            duration,
            data,
            dateModified,
            albumId,
            albumName ?: "",
            artistId,
            artistName ?: "",
            composer ?: "",
            albumArtist ?: ""
        )
    }

    @JvmOverloads
    fun makeSongCursor(
        selection: String?,
        selectionValues: Array<String>?,
        sortOrder: String = PreferenceUtil.songSortOrder,
        ignoreBlacklist: Boolean = false
    ): Cursor? {
        var selectionFinal = selection
        var selectionValuesFinal = selectionValues
        if (!ignoreBlacklist) {
            selectionFinal = if (selection != null && selection.trim { it <= ' ' } != "") {
                "$IS_MUSIC AND $selectionFinal"
            } else {
                IS_MUSIC
            }

            // Whitelist
            if (PreferenceUtil.isWhiteList) {
                selectionFinal =
                    selectionFinal + " AND " + Constants.DATA + " LIKE ?"
                selectionValuesFinal = addSelectionValues(
                    selectionValuesFinal, arrayListOf(
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).canonicalPath
                    )
                )
            } else {
                // Blacklist
//                val paths = BlacklistStore.getInstance(context).paths
//                if (paths.isNotEmpty()) {
//                    selectionFinal = generateBlacklistSelection(selectionFinal, paths.size)
//                    selectionValuesFinal = addSelectionValues(selectionValuesFinal, paths)
//                }
            }

            selectionFinal =
                selectionFinal + " AND " + MediaStore.Audio.Media.DURATION + ">= " + (PreferenceUtil.filterLength * 1000)
        }
        val uri = if (VersionUtils.hasQ()) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
        return try {
            context.contentResolver.query(
                uri,
                baseProjection,
                selectionFinal,
                selectionValuesFinal,
                sortOrder
            )
        } catch (ex: SecurityException) {
            return null
        }
    }


    private fun addSelectionValues(
        selectionValues: Array<String>?,
        paths: ArrayList<String>
    ): Array<String> {
        var selectionValuesFinal = selectionValues
        if (selectionValuesFinal == null) {
            selectionValuesFinal = emptyArray()
        }
        val newSelectionValues = Array(selectionValuesFinal.size + paths.size) {
            "n = $it"
        }
        System.arraycopy(selectionValuesFinal, 0, newSelectionValues, 0, selectionValuesFinal.size)
        for (i in selectionValuesFinal.size until newSelectionValues.size) {
            newSelectionValues[i] = paths[i - selectionValuesFinal.size] + "%"
        }
        return newSelectionValues
    }
}