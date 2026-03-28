package com.wall.app.wallpaper.tool.text

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

data class TextWallpaperOptions(
    val width: Int,
    val height: Int,
    val textSize: Float,
    val startColor: Int,
    val endColor: Int,
    val textColor: Int,
)

object TextWallpaperGenerator {
    fun generate(text: String, options: TextWallpaperOptions): Bitmap {
        val bitmap = Bitmap.createBitmap(options.width, options.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                0f,
                0f,
                options.width.toFloat(),
                options.height.toFloat(),
                options.startColor,
                options.endColor,
                Shader.TileMode.CLAMP,
            )
        }
        canvas.drawRect(0f, 0f, options.width.toFloat(), options.height.toFloat(), bgPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = options.textColor
            textAlign = Paint.Align.CENTER
            textSize = options.textSize
            setShadowLayer(10f, 2f, 2f, 0x55000000)
        }

        val x = options.width / 2f
        val y = options.height / 2f
        canvas.drawText(text, x, y, textPaint)
        return bitmap
    }
}
