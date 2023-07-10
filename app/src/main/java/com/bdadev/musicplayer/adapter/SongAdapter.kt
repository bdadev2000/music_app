package com.bdadev.musicplayer.adapter

import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import code.name.monkey.retromusic.util.RetroUtil
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.adapter.base.AbsMultiSelectAdapter
import com.bdadev.musicplayer.adapter.base.MediaEntryViewHolder
import com.bdadev.musicplayer.glide.RetroGlideExtension
import com.bdadev.musicplayer.glide.RetroGlideExtension.asBitmapPalette
import com.bdadev.musicplayer.glide.RetroMusicColoredTarget
import com.bdadev.musicplayer.helper.menu.SongMenuHelper
import com.bdadev.musicplayer.model.Song
import com.bdadev.musicplayer.util.PreferenceUtil
import com.bdadev.musicplayer.util.color.MediaNotificationProcessor
import com.bumptech.glide.Glide
import me.zhanghai.android.fastscroll.PopupTextProvider

open class SongAdapter(
    override val activity: FragmentActivity,
    var dataSet: MutableList<Song>,
    protected var itemLayoutRes: Int,
    showSectionName: Boolean = true
) : AbsMultiSelectAdapter<SongAdapter.ViewHolder, Song>(
    activity,
    R.menu.menu_media_selection
), PopupTextProvider {

    private var showSectionName = true

    init {
        this.showSectionName = showSectionName
        this.setHasStableIds(true)
    }

    open fun swapDataSet(dataSet: List<Song>) {
        this.dataSet = ArrayList(dataSet)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return dataSet[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            try {
                LayoutInflater.from(activity).inflate(itemLayoutRes, parent, false)
            } catch (e: Resources.NotFoundException) {
                LayoutInflater.from(activity).inflate(R.layout.item_list, parent, false)
            }
        return createViewHolder(view)
    }

    protected open fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = dataSet[position]
        val isChecked = isChecked(song)
        holder.itemView.isActivated = isChecked
        holder.menu?.isGone = isChecked
        holder.title?.text = getSongTitle(song)
        holder.text?.text = getSongText(song)
        holder.text2?.text = getSongText(song)
        loadAlbumCover(song, holder)
        val landscape = RetroUtil.isLandscape
//        if ((PreferenceUtil.songGridSize > 2 && !landscape) || (PreferenceUtil.songGridSizeLand > 5 && landscape)) {
//            holder.menu?.isVisible = false
//        }
    }

    private fun setColors(color: MediaNotificationProcessor, holder: ViewHolder) {
        if (holder.paletteColorContainer != null) {
            holder.title?.setTextColor(color.primaryTextColor)
            holder.text?.setTextColor(color.secondaryTextColor)
            holder.paletteColorContainer?.setBackgroundColor(color.backgroundColor)
            holder.menu?.imageTintList = ColorStateList.valueOf(color.primaryTextColor)
        }
        holder.mask?.backgroundTintList = ColorStateList.valueOf(color.primaryTextColor)
    }

    protected open fun loadAlbumCover(song: Song, holder: ViewHolder) {
        if (holder.image == null) {
            return
        }
        Glide.with(activity)
            .asBitmapPalette()
            .load(RetroGlideExtension.getSongModel(song))
            .into(object : RetroMusicColoredTarget(holder.image!!) {
                override fun onColorReady(colors: MediaNotificationProcessor) {
                    setColors(colors, holder)
                }
            })
    }

    private fun getSongTitle(song: Song): String {
        return song.title
    }

    private fun getSongText(song: Song): String {
        return song.artistName
    }

    private fun getSongText2(song: Song): String {
        return song.albumName
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getIdentifier(position: Int): Song? {
        return dataSet[position]
    }

    override fun getName(model: Song): String {
        return model.title
    }

    override fun onMultipleItemAction(menuItem: MenuItem, selection: List<Song>) {
//        SongsMenuHelper.handleMenuClick(activity, selection, menuItem.itemId)
    }

    override fun getPopupText(position: Int): String {
//        val sectionName: String? = when (PreferenceUtil.songSortOrder) {
//            SortOrder.SongSortOrder.SONG_DEFAULT -> return MusicUtil.getSectionName(
//                dataSet[position].title,
//                true
//            )
//
//            SortOrder.SongSortOrder.SONG_A_Z, SortOrder.SongSortOrder.SONG_Z_A -> dataSet[position].title
//            SortOrder.SongSortOrder.SONG_ALBUM -> dataSet[position].albumName
//            SortOrder.SongSortOrder.SONG_ARTIST -> dataSet[position].artistName
//            SortOrder.SongSortOrder.SONG_YEAR -> return MusicUtil.getYearString(dataSet[position].year)
//            SortOrder.SongSortOrder.COMPOSER -> dataSet[position].composer
//            SortOrder.SongSortOrder.SONG_ALBUM_ARTIST -> dataSet[position].albumArtist
//            else -> {
//                return ""
//            }
//        }
//        return MusicUtil.getSectionName(sectionName)
        return ""
    }

    open inner class ViewHolder(itemView: View) : MediaEntryViewHolder(itemView) {
        protected open var songMenuRes = SongMenuHelper.MENU_RES
        protected open val song: Song
            get() = dataSet[layoutPosition]

        init {
            menu?.setOnClickListener(object : SongMenuHelper.OnClickSongMenu(activity) {
                override val song: Song
                    get() = this@ViewHolder.song

                override val menuRes: Int
                    get() = songMenuRes

                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return onSongMenuItemClick(item) || super.onMenuItemClick(item)
                }
            })
        }

        protected open fun onSongMenuItemClick(item: MenuItem): Boolean {
//            if (image != null && image!!.isVisible) {
//                when (item.itemId) {
//                    R.id.action_go_to_album -> {
//                        activity.findNavController(R.id.fragment_container)
//                            .navigate(
//                                R.id.albumDetailsFragment,
//                                bundleOf(EXTRA_ALBUM_ID to song.albumId)
//                            )
//                        return true
//                    }
//                }
//            }
            return false
        }

        override fun onClick(v: View?) {
            if (isInQuickSelectMode) {
                toggleChecked(layoutPosition)
            } else {
//                MusicPlayerRemote.openQueue(dataSet, layoutPosition, true)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            println("Long click")
            return toggleChecked(layoutPosition)
        }
    }

    companion object {
        val TAG: String = SongAdapter::class.java.simpleName
    }
}

