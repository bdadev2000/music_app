package com.bdadev.musicplayer.model

open class Song(
    open val id: Long,
    open val title: String,
    open val trackNumber: Int,
    open val year: Int,
    open val duration: Long,
    open val data: String,
    open val dateModified: Long,
    open val albumId: Long,
    open val albumName: String,
    open val artistId: Long,
    open val artistName: String,
    open val composer: String?,
    open val albumArtist: String?
)
