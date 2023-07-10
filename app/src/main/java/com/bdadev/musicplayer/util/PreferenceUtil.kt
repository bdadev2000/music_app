package com.bdadev.musicplayer.util

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.bdadev.musicplayer.Constants.FILTER_SONG
import com.bdadev.musicplayer.Constants.IGNORE_MEDIA_STORE_ARTWORK
import com.bdadev.musicplayer.Constants.KEEP_SCREEN_ON
import com.bdadev.musicplayer.Constants.LANGUAGE_NAME
import com.bdadev.musicplayer.Constants.LAST_CHANGELOG_VERSION
import com.bdadev.musicplayer.Constants.LAST_USED_TAB
import com.bdadev.musicplayer.Constants.LIBRARY_CATEGORIES
import com.bdadev.musicplayer.Constants.LOCALE_AUTO_STORE_ENABLED
import com.bdadev.musicplayer.Constants.REMEMBER_LAST_TAB
import com.bdadev.musicplayer.Constants.SONG_GRID_SIZE
import com.bdadev.musicplayer.Constants.SONG_GRID_SIZE_LAND
import com.bdadev.musicplayer.Constants.SONG_GRID_STYLE
import com.bdadev.musicplayer.Constants.SONG_SORT_ORDER
import com.bdadev.musicplayer.Constants.TAB_TEXT_MODE
import com.bdadev.musicplayer.Constants.TOGGLE_FULL_SCREEN
import com.bdadev.musicplayer.Constants.WHITELIST_MUSIC
import com.bdadev.musicplayer.R
import com.bdadev.musicplayer.application.MyApplication
import com.bdadev.musicplayer.extensions.getIntRes
import com.bdadev.musicplayer.extensions.getStringOrDefault
import com.bdadev.musicplayer.fragments.GridStyle
import com.bdadev.musicplayer.helper.SortOrder
import com.bdadev.musicplayer.model.CategoryInfo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

object PreferenceUtil {
    private val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())

    val defaultCategories = listOf(
        CategoryInfo(CategoryInfo.Category.Home, true),
        CategoryInfo(CategoryInfo.Category.Songs, true),
        CategoryInfo(CategoryInfo.Category.Albums, true),
        CategoryInfo(CategoryInfo.Category.Artists, true),
        CategoryInfo(CategoryInfo.Category.Playlists, true),
        CategoryInfo(CategoryInfo.Category.Genres, false),
        CategoryInfo(CategoryInfo.Category.Folder, false),
        CategoryInfo(CategoryInfo.Category.Search, false)
    )

    var songSortOrder
        get() = sharedPreferences.getStringOrDefault(
            SONG_SORT_ORDER,
            SortOrder.SongSortOrder.SONG_A_Z
        )
        set(value) = sharedPreferences.edit {
            putString(SONG_SORT_ORDER,value)
        }

    val isWhiteList : Boolean
        get() = sharedPreferences.getBoolean(WHITELIST_MUSIC,false)

    val filterLength get() = sharedPreferences.getInt(FILTER_SONG, 20)

    var languageCode: String
        get() = sharedPreferences.getString(LANGUAGE_NAME, "auto") ?: "auto"
        set(value) = sharedPreferences.edit {
            putString(LANGUAGE_NAME, value)
        }

    var isLocaleAutoStorageEnabled: Boolean
        get() = sharedPreferences.getBoolean(
            LOCALE_AUTO_STORE_ENABLED,
            false
        )
        set(value) = sharedPreferences.edit {
            putBoolean(LOCALE_AUTO_STORE_ENABLED, value)
        }

    val isFullScreenMode
        get() = sharedPreferences.getBoolean(
            TOGGLE_FULL_SCREEN, false
        )

    val isScreenOnEnabled get() = sharedPreferences.getBoolean(KEEP_SCREEN_ON, false)

    val rememberLastTab: Boolean
        get() = sharedPreferences.getBoolean(REMEMBER_LAST_TAB, true)

    var libraryCategory: List<CategoryInfo>
        get() {
            val gson = Gson()
            val collectionType = object : TypeToken<List<CategoryInfo>>() {}.type

            val data = sharedPreferences.getStringOrDefault(
                LIBRARY_CATEGORIES,
                gson.toJson(defaultCategories, collectionType)
            )
            return try {
                Gson().fromJson(data, collectionType)
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                return defaultCategories
            }
        }
        set(value) {
            val collectionType = object : TypeToken<List<CategoryInfo?>?>() {}.type
            sharedPreferences.edit {
                putString(LIBRARY_CATEGORIES, Gson().toJson(value, collectionType))
            }
        }

    var lastTab: Int
        get() = sharedPreferences
            .getInt(LAST_USED_TAB, 0)
        set(value) = sharedPreferences.edit { putInt(LAST_USED_TAB, value) }

    val tabTitleMode: Int
        get() {
            return when (sharedPreferences.getStringOrDefault(
                TAB_TEXT_MODE, "0"
            ).toInt()) {
                0 -> BottomNavigationView.LABEL_VISIBILITY_AUTO
                1 -> BottomNavigationView.LABEL_VISIBILITY_LABELED
                2 -> BottomNavigationView.LABEL_VISIBILITY_SELECTED
                3 -> BottomNavigationView.LABEL_VISIBILITY_UNLABELED
                else -> BottomNavigationView.LABEL_VISIBILITY_LABELED
            }
        }
    var lastVersion
        // This was stored as an integer before now it's a long, so avoid a ClassCastException
        get() = try {
            sharedPreferences.getLong(LAST_CHANGELOG_VERSION, 0)
        } catch (e: ClassCastException) {
            sharedPreferences.edit { remove(LAST_CHANGELOG_VERSION) }
            0
        }
        set(value) = sharedPreferences.edit {
            putLong(LAST_CHANGELOG_VERSION, value)
        }

    val isIgnoreMediaStoreArtwork
        get() = sharedPreferences.getBoolean(
            IGNORE_MEDIA_STORE_ARTWORK,
            false
        )

    var songGridSize
        get() = sharedPreferences.getInt(
            SONG_GRID_SIZE,
            MyApplication.getContext().getIntRes(R.integer.default_list_columns)
        )
        set(value) = sharedPreferences.edit {
            putInt(SONG_GRID_SIZE, value)
        }
    var songGridSizeLand
        get() = sharedPreferences.getInt(
            SONG_GRID_SIZE_LAND,
            MyApplication.getContext().getIntRes(R.integer.default_grid_columns_land)
        )
        set(value) = sharedPreferences.edit {
            putInt(SONG_GRID_SIZE_LAND, value)
        }

    var songGridStyle: GridStyle
        get() {
            val id: Int = sharedPreferences.getInt(SONG_GRID_STYLE, 0)
            // We can directly use "first" kotlin extension function here but
            // there maybe layout id stored in this so to avoid a crash we use
            // "firstOrNull"
            return GridStyle.values().firstOrNull { gridStyle ->
                gridStyle.id == id
            } ?: GridStyle.Grid
        }
        set(value) = sharedPreferences.edit {
            putInt(SONG_GRID_STYLE, value.id)
        }
}