package com.bdadev.musicplayer.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bdadev.musicplayer.interfaces.IMusicServiceEventListener
import com.bdadev.musicplayer.model.Home
import com.bdadev.musicplayer.model.Song
import com.bdadev.musicplayer.repository.RealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val repository: RealRepository,
) : ViewModel(), IMusicServiceEventListener {
    private val home = MutableLiveData<List<Home>>()
    private val songs = MutableLiveData<List<Song>>()

    init {
        loadLibraryContent()
    }

    private fun loadLibraryContent() = viewModelScope.launch(Dispatchers.IO) {
        fetchHomeSections()
//        fetchSuggestions()
        fetchSongs()
//        fetchAlbums()
//        fetchArtists()
//        fetchGenres()
//        fetchPlaylists()
    }

    fun getSongs(): LiveData<List<Song>> = songs


    private suspend fun fetchSongs() {
        songs.postValue(repository.allSongs())
    }


    private suspend fun fetchHomeSections() {
        home.postValue(repository.homeSections())
    }

    override fun onServiceConnected() {
    }

    override fun onServiceDisconnected() {
    }

    override fun onQueueChanged() {
    }

    override fun onFavoriteStateChanged() {
    }

    override fun onPlayingMetaChanged() {
    }

    override fun onPlayStateChanged() {
    }

    override fun onRepeatModeChanged() {
    }

    override fun onShuffleModeChanged() {
    }

    override fun onMediaStoreChanged() {
    }

}