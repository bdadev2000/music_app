package com.bdadev.musicplayer.activities

import android.content.Intent
import android.os.Bundle
import androidx.navigation.contains
import androidx.navigation.ui.setupWithNavController
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.activities.base.AbsCastActivity
import com.bdadev.musicplayer.extensions.currentFragment
import com.bdadev.musicplayer.extensions.findNavController
import com.bdadev.musicplayer.interfaces.IScrollHelper
import com.bdadev.musicplayer.model.CategoryInfo
import com.bdadev.musicplayer.util.PreferenceUtil

class MainActivity: AbsCastActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val EXPAND_PANEL = "expand_panel"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigationController()
        WhatsNewFragment.showChangeLog(this)

    }

    private fun setupNavigationController() {
        val navController = findNavController(R.id.fragment_container)
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.main_graph)

        val categoryInfo: CategoryInfo = PreferenceUtil.libraryCategory.first { it.visible }
        if (categoryInfo.visible) {
            if (!navGraph.contains(PreferenceUtil.lastTab)) PreferenceUtil.lastTab =
                categoryInfo.category.id
            navGraph.setStartDestination(
                if (PreferenceUtil.rememberLastTab) {
                    PreferenceUtil.lastTab.let {
                        if (it == 0) {
                            categoryInfo.category.id
                        } else {
                            it
                        }
                    }
                } else categoryInfo.category.id
            )
        }
        navController.graph = navGraph
        navigationView.setupWithNavController(navController)
        // Scroll Fragment to top
        navigationView.setOnItemReselectedListener {
            currentFragment(R.id.fragment_container).apply {
                if (this is IScrollHelper) {
                    scrollToTop()
                }
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == navGraph.startDestinationId) {
                currentFragment(R.id.fragment_container)?.enterTransition = null
            }
            when (destination.id) {
                R.id.action_home, R.id.action_song, R.id.action_album, R.id.action_artist, R.id.action_folder, R.id.action_playlist, R.id.action_genre, R.id.action_search -> {
                    // Save the last tab
                    if (PreferenceUtil.rememberLastTab) {
                        saveTab(destination.id)
                    }
                    // Show Bottom Navigation Bar
                    setBottomNavVisibility(visible = true, animate = true)
                }
                R.id.playing_queue_fragment -> {
                    setBottomNavVisibility(visible = false, hideBottomSheet = true)
                }
                else -> setBottomNavVisibility(
                    visible = false,
                    animate = true
                ) // Hide Bottom Navigation Bar
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.fragment_container).navigateUp()
    private fun saveTab(id: Int) {
        if (PreferenceUtil.libraryCategory.firstOrNull { it.category.id == id }?.visible == true) {
            PreferenceUtil.lastTab = id
        }
    }

}
