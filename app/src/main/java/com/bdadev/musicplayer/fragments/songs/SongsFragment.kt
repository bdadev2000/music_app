package com.bdadev.musicplayer.fragments.songs

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import code.name.monkey.retromusic.util.RetroUtil
import com.bdadev.musicplayer.adapter.SongAdapter
import com.bdadev.musicplayer.fragments.GridStyle
import com.bdadev.musicplayer.fragments.base.AbsRecyclerViewCustomGridSizeFragment
import com.bdadev.musicplayer.util.PreferenceUtil

class SongsFragment : AbsRecyclerViewCustomGridSizeFragment<SongAdapter, GridLayoutManager>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        libraryViewModel.getSongs().observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                adapter?.swapDataSet(it)
            else
                adapter?.swapDataSet(listOf())
        }
    }

    override fun setGridSize(gridSize: Int) {
        adapter?.notifyDataSetChanged()
    }

    override fun setSortOrder(sortOrder: String) {
//        libraryViewModel.forceReload(ReloadType.Songs)
    }

    override fun loadSortOrder(): String {
        return PreferenceUtil.songSortOrder
    }

    override fun saveSortOrder(sortOrder: String) {
        PreferenceUtil.songSortOrder = sortOrder
    }

    override fun loadGridSize(): Int {
        return PreferenceUtil.songGridSize
    }

    override fun saveGridSize(gridColumns: Int) {
        PreferenceUtil.songGridSize = gridColumns
    }

    override fun loadGridSizeLand(): Int {
        return PreferenceUtil.songGridSizeLand
    }

    override fun saveGridSizeLand(gridColumns: Int) {
        PreferenceUtil.songGridSizeLand = gridColumns
    }

    override fun loadLayoutRes(): Int {
        return PreferenceUtil.songGridStyle.layoutResId
    }

    override fun saveLayoutRes(layoutRes: Int) {
        PreferenceUtil.songGridStyle = GridStyle.values().first { gridStyle ->
            gridStyle.layoutResId == layoutRes
        }
    }


    override fun createLayoutManager(): GridLayoutManager {
        return GridLayoutManager(requireActivity(), getGridSize())
    }

    override fun createAdapter(): SongAdapter {
        val dataSet = if (adapter == null) mutableListOf() else adapter!!.dataSet
        return SongAdapter(
            requireActivity(),
            dataSet,
            itemLayoutRes()
        )
    }

    override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateMenu(menu, inflater)
//        val gridSizeItem: MenuItem = menu.findItem(R.id.action_grid_size)
//        if (RetroUtil.isLandscape) {
//            gridSizeItem.setTitle(R.string.action_grid_size_land)
//        }
//        setUpGridSizeMenu(gridSizeItem.subMenu!!)
//        val layoutItem = menu.findItem(R.id.action_layout_type)
//        setupLayoutMenu(layoutItem.subMenu!!)
//        setUpSortOrderMenu(menu.findItem(R.id.action_sort_order).subMenu!!)
//        //Setting up cast button
//        requireContext().setUpMediaRouteButton(menu)
    }

    override fun onMenuItemSelected(item: MenuItem): Boolean {
//        if (handleGridSizeMenuItem(item)) {
//            return true
//        }
//        if (handleLayoutResType(item)) {
//            return true
//        }
//        if (handleSortOrderMenuItem(item)) {
//            return true
//        }
//        return super.onMenuItemSelected(item)
        return false
    }

}