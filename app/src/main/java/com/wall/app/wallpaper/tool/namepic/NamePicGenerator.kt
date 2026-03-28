package com.wall.app.wallpaper.tool.namepic

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

object NamePicGenerator {
    fun renderName(name: String, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        canvas.drawARGB(255, 20, 20, 20)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
            textSize = (width.coerceAtMost(height) * 0.3f)
            color = 0xFFFFFFFF.toInt()
        }
        canvas.drawText(name.take(1), width / 2f, height / 2f, paint)
        return bitmap
    }
}
