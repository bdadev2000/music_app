package com.bdadev.musicplayer

import com.bdadev.musicplayer.fragments.viewmodel.SongViewModel
import com.bdadev.musicplayer.repository.RealRepository
import com.bdadev.musicplayer.repository.RealSongRepository
import com.bdadev.musicplayer.repository.Repository
import com.bdadev.musicplayer.repository.SongRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

private val dataModule = module {
    single {
        RealRepository(
            get(),
            get(),
        )
    } bind Repository::class

    single {
        RealSongRepository(get())
    } bind SongRepository::class

}

private val viewModules = module {

    viewModel {
        SongViewModel(get())
    }

}

val appModules = listOf(dataModule,viewModules)