package com.bdadev.musicplayer.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.bdadev.musicplayer.activities.base.AbsMusicServiceActivity
import com.bdadev.musicplayer.databinding.ActivityPermissionBinding
import com.bdadev.musicplayer.extensions.show
import com.bdadev.musicplayer.util.VersionUtils

class PermissionActivity : AbsMusicServiceActivity() {
    private lateinit var binding: ActivityPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAccessStorage.setOnClickListener {
            requestPermissions()
        }
        if (VersionUtils.hasMarshmallow()) {
            binding.btnAccessSetting.show()
            binding.btnAccessSetting.setOnClickListener {
                if(!hasAudioPermission()){
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.data = ("package:" + applicationContext.packageName).toUri()
                    startActivity(intent)
                }
            }
        }
        binding.btnLetStart.setOnClickListener {
            if (hasPermissions()) {
                startActivity(
                    Intent(this, MainActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                )
                finish()
            }
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
                remove()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.btnLetStart.isEnabled = hasStoragePermission()
        if(hasStoragePermission()){
            binding.checkImgInStorage.visibility = View.VISIBLE
        }
        if (VersionUtils.hasMarshmallow()) {
            if (hasAudioPermission()) {
                binding.checkImgInSetting.visibility = View.VISIBLE
            }
        }
    }

    private fun hasStoragePermission(): Boolean {
        return hasPermissions()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasAudioPermission(): Boolean {
        return Settings.System.canWrite(this)
    }
}