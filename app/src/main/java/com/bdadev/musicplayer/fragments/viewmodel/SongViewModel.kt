package com.bdadev.musicplayer.fragments.viewmodel

import androidx.lifecycle.*
import com.bdadev.musicplayer.model.Song
import com.bdadev.musicplayer.repository.RealRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SongViewModel(private val repository: RealRepository) : ViewModel(){

    private val songs = MutableLiveData<List<Song>>()

    init {
        loadSongContent()
    }
    fun getSongs(): LiveData<List<Song>> = songs


    private fun loadSongContent() = viewModelScope.launch(IO) {
        fetchSongs()
    }

    private suspend fun fetchSongs() {
        songs.postValue(repository.allSongs())
    }
}
