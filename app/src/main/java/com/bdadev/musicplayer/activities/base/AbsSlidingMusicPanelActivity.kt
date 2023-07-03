package com.bdadev.musicplayer.activities.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import code.name.monkey.retromusic.extensions.getBottomInsets
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.activities.PermissionActivity
import com.bdadev.musicplayer.databinding.SlidingMusicPanelLayoutBinding
import com.bdadev.musicplayer.extensions.dip
import com.bdadev.musicplayer.extensions.hide
import com.bdadev.musicplayer.extensions.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior

abstract class AbsSlidingMusicPanelActivity : AbsMusicServiceActivity(), SharedPreferences.OnSharedPreferenceChangeListener{
    companion object {
        val TAG: String = AbsSlidingMusicPanelActivity::class.java.simpleName
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private val panelState: Int
        get() = bottomSheetBehavior.state
    private lateinit var binding: SlidingMusicPanelLayoutBinding
    private var isInOneTabMode = false
    private var windowInsets: WindowInsetsCompat? = null


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Handle back press ${bottomSheetBehavior.state}")
            if (!handleBackPress()) {
                remove()
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermissions()) {
            startActivity(Intent(this, PermissionActivity::class.java))
            finish()
        }
        binding = SlidingMusicPanelLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomSheet()

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

    }
    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.slidingPanel)
//        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallbackList)
//        bottomSheetBehavior.isHideable = PreferenceUtil.swipeDownToDismiss
//        bottomSheetBehavior.significantVelocityThreshold = 300
//        setMiniPlayerAlphaProgress(0F)
    }
    val navigationView get() = binding.navigationView

    val slidingPanel get() = binding.slidingPanel

    //TODO: hideBottomSheet: Boolean = MusicPlayerRemote.playingQueue.isEmpty(),
    fun setBottomNavVisibility(
        visible: Boolean,
        animate: Boolean = false,
        hideBottomSheet: Boolean = false,
    ) {
        if (isInOneTabMode) {
            hideBottomSheet(
                hide = hideBottomSheet,
                animate = animate,
                isBottomNavVisible = false
            )
            return
        }
        if (visible xor navigationView.isVisible) {
            val mAnimate = animate && bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED
            if (mAnimate) {
                if (visible) {
                    binding.navigationView.bringToFront()
                    binding.navigationView.show()
                } else {
                    binding.navigationView.hide()
                }
            } else {
                binding.navigationView.isVisible = visible
                if (visible && bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                    binding.navigationView.bringToFront()
                }
            }
        }
        hideBottomSheet(
            hide = hideBottomSheet,
            animate = animate,
            isBottomNavVisible = visible && navigationView is BottomNavigationView
        )
    }

    fun hideBottomSheet(
        hide: Boolean,
        animate: Boolean = false,
        isBottomNavVisible: Boolean = navigationView.isVisible && navigationView is BottomNavigationView,
    ) {
        val heightOfBar = windowInsets.getBottomInsets() + dip(R.dimen.mini_player_height)
        val heightOfBarWithTabs = heightOfBar + dip(R.dimen.bottom_nav_height)
        if (hide) {
            bottomSheetBehavior.peekHeight = -windowInsets.getBottomInsets()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            libraryViewModel.setFabMargin(
//                this,
//                if (isBottomNavVisible) dip(R.dimen.bottom_nav_height) else 0
//            )
        } else {
//            if (MusicPlayerRemote.playingQueue.isNotEmpty()) {
//                binding.slidingPanel.elevation = 0F
//                binding.navigationView.elevation = 5F
//                if (isBottomNavVisible) {
//                    logD("List")
//                    if (animate) {
//                        bottomSheetBehavior.peekHeightAnimate(heightOfBarWithTabs)
//                    } else {
//                        bottomSheetBehavior.peekHeight = heightOfBarWithTabs
//                    }
//                    libraryViewModel.setFabMargin(
//                        this,
//                        dip(R.dimen.bottom_nav_mini_player_height)
//                    )
//                } else {
//                    logD("Details")
//                    if (animate) {
//                        bottomSheetBehavior.peekHeightAnimate(heightOfBar).doOnEnd {
//                            binding.slidingPanel.bringToFront()
//                        }
//                    } else {
//                        bottomSheetBehavior.peekHeight = heightOfBar
//                        binding.slidingPanel.bringToFront()
//                    }
//                    libraryViewModel.setFabMargin(this, dip(R.dimen.mini_player_height))
//                }
//            }
        }
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }

    private fun handleBackPress(): Boolean {
        if (panelState == BottomSheetBehavior.STATE_EXPANDED) {
            collapsePanel()
            return true
        }
        return false
    }

    fun collapsePanel() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}