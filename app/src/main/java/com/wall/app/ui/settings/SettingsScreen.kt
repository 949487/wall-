package com.wall.app.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    val globalWallpaperEnabled = remember { mutableStateOf(true) }
    val dynamicColor = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("设置", style = MaterialTheme.typography.headlineSmall)
        SettingItem("动态壁纸总开关", "控制所有动态壁纸能力") {
            Switch(
                checked = globalWallpaperEnabled.value,
                onCheckedChange = { globalWallpaperEnabled.value = it },
            )
        }
        SettingItem("动态配色", "Material 3 Expressive 动态主题") {
            Switch(
                checked = dynamicColor.value,
                onCheckedChange = { dynamicColor.value = it },
            )
        }
        SettingItem("关于", "版本 0.1.0") {
            Text("Jetpack Compose + Material 3", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    trailing: @Composable () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(subtitle, style = MaterialTheme.typography.bodySmall)
            trailing()
        }
    }
}
