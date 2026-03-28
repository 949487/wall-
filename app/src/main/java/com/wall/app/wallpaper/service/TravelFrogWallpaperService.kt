package com.wall.app.wallpaper.service

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import kotlin.math.cos
import kotlin.math.sin

class TravelFrogWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = TravelFrogEngine()

    private inner class TravelFrogEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val frogPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.GREEN }
        private val snailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.YELLOW }
        private var visible = false

        private val frame = object : Runnable {
            override fun run() {
                drawFrame()
                if (visible) handler.postDelayed(this, 16)
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) frame.run() else handler.removeCallbacks(frame)
        }

        override fun onSurfaceDestroyed(holder: android.view.SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            visible = false
            handler.removeCallbacks(frame)
        }

        private fun drawFrame() {
            val canvas: Canvas = surfaceHolder.lockCanvas() ?: return
            try {
                val cx = canvas.width / 2f
                val cy = canvas.height / 2f
                val radius = canvas.width.coerceAtMost(canvas.height) * 0.35f

                canvas.drawColor(Color.rgb(28, 32, 39))
                canvas.drawCircle(cx, cy, 48f, frogPaint)

                val now = System.currentTimeMillis()
                val secInMinute = (now / 1000L) % 60L
                val angle = (secInMinute / 60f) * (2f * Math.PI.toFloat())
                val sx = cx + radius * cos(angle)
                val sy = cy + radius * sin(angle)
                canvas.drawCircle(sx, sy, 20f, snailPaint)
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
