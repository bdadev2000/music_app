package com.bdadev.musicplayer.activities.base

import android.Manifest
import android.os.Bundle
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.util.VersionUtils

abstract class AbsMusicServiceActivity : AbsBaseActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setPermissionDeniedMessage(getString(R.string.permission_external_storage_denied))
    }

    override fun onDestroy() {
        super.onDestroy()
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