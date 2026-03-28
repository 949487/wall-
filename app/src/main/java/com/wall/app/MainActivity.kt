package com.wall.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wall.app.ui.navigation.WallApp
import com.wall.app.ui.theme.WallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallTheme {
                WallApp()
            }
        }
    }
}
