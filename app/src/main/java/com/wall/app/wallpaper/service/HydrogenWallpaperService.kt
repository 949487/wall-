package com.wall.app.wallpaper.service

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import kotlin.random.Random

class HydrogenWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = HydrogenEngine()

    private inner class HydrogenEngine : Engine() {
        private val handler = Handler(Looper.getMainLooper())
        private val particles = MutableList(32) { Particle.random() }
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
        private var visible = false

        private val drawRunnable = object : Runnable {
            override fun run() {
                drawFrame()
                if (visible) handler.postDelayed(this, 16)
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) drawRunnable.run() else handler.removeCallbacks(drawRunnable)
        }

        override fun onSurfaceDestroyed(holder: android.view.SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            visible = false
            handler.removeCallbacks(drawRunnable)
        }

        private fun drawFrame() {
            val canvas: Canvas = surfaceHolder.lockCanvas() ?: return
            try {
                canvas.drawColor(Color.BLACK)
                particles.forEach { p ->
                    p.update(canvas.width.toFloat(), canvas.height.toFloat())
                    canvas.drawCircle(p.x, p.y, p.radius, paint)
                }
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private data class Particle(
        var x: Float,
        var y: Float,
        var vx: Float,
        var vy: Float,
        var radius: Float,
    ) {
        fun update(maxW: Float, maxH: Float) {
            x += vx
            y += vy
            if (x < 0 || x > maxW) vx = -vx
            if (y < 0 || y > maxH) vy = -vy
        }

        companion object {
            fun random() = Particle(
                x = Random.nextFloat() * 1200f,
                y = Random.nextFloat() * 2600f,
                vx = Random.nextFloat() * 4f - 2f,
                vy = Random.nextFloat() * 4f - 2f,
                radius = Random.nextFloat() * 4f + 2f,
            )
        }
    }
}
