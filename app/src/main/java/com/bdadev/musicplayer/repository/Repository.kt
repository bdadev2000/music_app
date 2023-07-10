package com.bdadev.musicplayer.repository

import android.content.Context
import com.bdadev.musicplayer.model.Home
import com.bdadev.musicplayer.model.Song

interface Repository {
    suspend fun allSongs(): List<Song>

    suspend fun homeSections(): List<Home>

}

class RealRepository(context: Context,private val songRepository: SongRepository,) : Repository {
    override suspend fun allSongs(): List<Song>  = songRepository.songs()
    override suspend fun homeSections(): List<Home> {
        val homeSections = mutableListOf<Home>()
        val sections: List<Home> = listOf(
//            topArtistsHome(),
//            topAlbumsHome(),
//            recentArtistsHome(),
//            recentAlbumsHome(),
//            favoritePlaylistHome()
        )
        for (section in sections) {
            if (section.arrayList.isNotEmpty()) {
                homeSections.add(section)
            }
        }
        return homeSections
    }
}