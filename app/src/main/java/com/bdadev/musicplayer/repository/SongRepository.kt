package com.bdadev.musicplayer.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.bdadev.musicplayer.model.Song

interface SongRepository{
    fun songs() : List<Song>
}

class RealSongRepository(private val context: Context) : SongRepository{
    override fun songs(): List<Song> {
        val tempAudioList: MutableList<Song> = ArrayList()

//        Uri uri1 = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST, MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.IS_MUSIC
        )
        //        Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Audio.Media.DATA + " like ? ", new String[]{"%yourFolderName%"}, null);
        val c: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        while (c?.moveToNext() == true) {
            val path: String = c.getString(0)
            val album: String = c.getString(1)
            val artist: String = c.getString(2)
            val duration: Long = c.getLong(3)
            val name = path.substring(path.lastIndexOf("/") + 1)
            val audio = Song(name, path, album, artist, duration)
//                Log.e("Name :" + name, " Album :" + album);
//                Log.e("Path :" + path, " Artist :" + artist);
            if (duration >= 200000) {
                tempAudioList.add(audio)
            }
        }
        c?.close()
        return tempAudioList
    }

}