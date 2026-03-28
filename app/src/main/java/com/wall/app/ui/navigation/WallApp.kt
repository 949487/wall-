package com.wall.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wall.app.ui.home.HomeScreen
import com.wall.app.ui.settings.SettingsScreen
import com.wall.app.ui.tools.ToolsScreen

private enum class Dest(val route: String, val label: String) {
    Home("home", "首页"),
    Tools("tools", "工具"),
    Settings("settings", "设置"),
}

@Composable
fun WallApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                Dest.entries.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute == dest.route,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            val icon = when (dest) {
                                Dest.Home -> Icons.Filled.Home
                                Dest.Tools -> Icons.Filled.Build
                                Dest.Settings -> Icons.Filled.Settings
                            }
                            Icon(icon, contentDescription = dest.label)
                        },
                        label = { Text(dest.label) },
                    )
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Dest.Home.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Dest.Home.route) { HomeScreen() }
            composable(Dest.Tools.route) { ToolsScreen() }
            composable(Dest.Settings.route) { SettingsScreen() }
        }
    }
}
