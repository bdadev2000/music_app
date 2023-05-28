package com.bdadev.musicplayer.fragments.song

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.fragments.viewmodel.SongViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SongFragment : Fragment() {

    private val songViewModel : SongViewModel by activityViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        songViewModel.getSongs().observe(viewLifecycleOwner, {
            if(it.isNotEmpty()){
                for(i in it){
                    Log.d("AAA", "===$it")
                }
            }
        })
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

}