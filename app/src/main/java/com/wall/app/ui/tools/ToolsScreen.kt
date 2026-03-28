package com.wall.app.ui.tools

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private data class ToolItem(
    val title: String,
    val subtitle: String,
)

@Composable
fun ToolsScreen() {
    val items = listOf(
        ToolItem("氢动态壁纸", "WallpaperService.Engine 粒子动态壁纸"),
        ToolItem("文字生成壁纸", "输入文字并一键应用桌面与锁屏"),
        ToolItem("旅行青蛙动态壁纸", "动作时间配置 + 蜗牛每分钟一圈算法"),
        ToolItem("姓氏壁纸", "单字/姓氏生成，支持渐变背景与自定义字体"),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("工具箱", style = MaterialTheme.typography.headlineSmall)
        }
        items(items) { tool ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(tool.title, style = MaterialTheme.typography.titleLarge)
                    Text(tool.subtitle, style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = { /* TODO: navigate to detail page */ }) {
                        Text("进入")
                    }
                }
            }
        }
    }
}
