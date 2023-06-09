package code.name.monkey.retromusic.extensions

import androidx.core.view.WindowInsetsCompat
import code.name.monkey.retromusic.util.RetroUtil
import com.bdadev.musicplayer.util.PreferenceUtil

fun WindowInsetsCompat?.getBottomInsets(): Int {
    return if (PreferenceUtil.isFullScreenMode) {
        return 0
    } else {
        this?.getInsets(WindowInsetsCompat.Type.systemBars())?.bottom ?: RetroUtil.navigationBarHeight
    }
}
