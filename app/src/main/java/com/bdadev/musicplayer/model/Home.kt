package com.bdadev.musicplayer.model

import androidx.annotation.StringRes
import com.bdadev.musicplayer.HomeSection

data class Home(
    val arrayList: List<Any>,
    @HomeSection
    val homeSection: Int,
    @StringRes
    val titleRes: Int
)