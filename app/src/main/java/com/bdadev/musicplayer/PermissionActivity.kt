package com.bdadev.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bdadev.musicplayer.databinding.ActivityPermissionBinding

class PermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }


}