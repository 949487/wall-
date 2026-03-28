package com.wall.app.wallpaper.tool.text

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap

object WallpaperApplier {
    fun applySystemAndLock(context: Context, bitmap: Bitmap): Result<Unit> {
        return runCatching {
            val manager = WallpaperManager.getInstance(context)
            manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
            runCatching {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            }
        }
    }
}
