package com.bdadev.musicplayer.repository

import android.content.Context
import com.bdadev.musicplayer.model.Song

interface Repository {
    suspend fun allSongs(): List<Song>
}

class RealRepository(context: Context,private val songRepository: SongRepository,) : Repository {
    override suspend fun allSongs(): List<Song>  = songRepository.songs()
}