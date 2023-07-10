package com.bdadev.musicplayer.activities.base

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.interfaces.IMusicServiceEventListener
import com.bdadev.musicplayer.util.VersionUtils

abstract class AbsMusicServiceActivity : AbsBaseActivity(){

    private val mMusicServiceEventListeners = ArrayList<IMusicServiceEventListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPermissionDeniedMessage(getString(R.string.permission_external_storage_denied))
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun addMusicServiceEventListener(listenerI: IMusicServiceEventListener?) {
        if (listenerI != null) {
            mMusicServiceEventListeners.add(listenerI)
        }
    }

    fun removeMusicServiceEventListener(listenerI: IMusicServiceEventListener?) {
        if (listenerI != null) {
            mMusicServiceEventListeners.remove(listenerI)
        }
    }

    override fun getPermissionsToRequest(): Array<String> {
        return mutableListOf<String>().apply {
            if (VersionUtils.hasT()) {
                add(Manifest.permission.READ_MEDIA_AUDIO)
                add(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (!VersionUtils.hasR()) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    }

    companion object {
        val TAG: String = AbsMusicServiceActivity::class.java.simpleName
    }
}