# wall 壁纸应用完整技术规划（Jetpack Compose + Material 3 Expressive）

## 1. 项目目标

基于 **Jetpack Compose** + **Material 3 Expressive**，实现三标签页壁纸应用：

1. **首页（Wallpaper）**：壁纸流 + XPath/JSONPath 规则源配置 + 自动更新应用壁纸。
2. **工具（Tools）**：整合四个开源项目核心能力：
   - H2Wallpaper（氢动态壁纸）
   - OnlyTextWallpaper（文字生成壁纸，一键设为桌面+锁屏）
   - Travel-Frog（旅行青蛙动态壁纸）
   - NamePic（姓氏/单字壁纸生成）
3. **设置（Settings）**：视觉风格保持 Tomato 项目设置页交互与层级。

---

## 2. 设计规范（Material 3 Expressive）

- **形状**：大圆角卡片（20–28dp），主按钮/Chip 高可触达。
- **排版**：标题使用 `headlineMedium/large`，强调字体粗细层次（SemiBold/Bold）。
- **颜色**：支持动态色（Dynamic Color）+ 预设色板回退。
- **动效**：页面切换与卡片状态变化使用轻量 spring，避免高频重绘。
- **结构**：三底栏固定：`首页 / 工具 / 设置`。

---

## 3. 分层架构

```text
app/
  ui/                 # Compose 层
    home/
    tools/
    settings/
    theme/
  domain/             # 用例/业务逻辑
  data/
    local/            # DataStore/Room（可选）
    remote/           # Retrofit/OkHttp/Jsoup
  wallpaper/
    service/          # WallpaperService.Engine
    renderer/         # 动态壁纸渲染器
  worker/
    WallpaperSyncWorker
```

- **MVI/MVVM 均可**，推荐 ViewModel + StateFlow。
- 首页规则数据源可持久化在 `DataStore(JSON)`，后续再升级 Room。

---

## 4. 导航与页面

### 4.1 首页（Wallpaper）

#### 关键功能
- 壁纸流展示（参考 Tomato 首页布局风格）。
- 规则源管理（新增/编辑/启停/测试/删除）。
- 手动应用壁纸、自动周期更新。

#### 规则模型（建议）

```kotlin
data class XPathSource(
    val id: String,
    val name: String,
    val sourceUrl: String,
    val ruleArticles: String,
    val ruleImage: String,
    val ruleLink: String,
    val headerJson: String = "{}",
    val enabled: Boolean = true,
    val updateIntervalMs: Long = 60 * 60 * 1000,
    val lastUpdateTime: Long = 0L,
    val lastError: String? = null,
)
```

#### 规则执行
- 对 JSON API：优先 JSONPath。
- 对 HTML 页面：使用 Jsoup + XPath（`selectXpath`）。
- 支持 Header、UA 注入、可选 Cookie Jar。
- 提供“测试规则”按钮：展示提取到的首图与错误信息。

### 4.2 工具（Tools）

提供四个入口卡片，卡片内直接显示“预览/配置/应用”三个动作。

#### 2.1 氢动态壁纸（H2Wallpaper）

- 基于 `WallpaperService.Engine` + `Handler`/`Choreographer`。
- 粒子参数：数量、速度、拖尾、颜色方案。
- 生命周期：`onVisibilityChanged` 暂停/恢复渲染；`onSurfaceDestroyed` 释放。

#### 2.2 文字生成壁纸（OnlyTextWallpaper）

- 输入文本 -> Canvas 生成 Bitmap。
- 支持：字体、渐变背景、描边、阴影、多行排版。
- 一键应用：
  - 先 `FLAG_SYSTEM`
  - 再尝试 `FLAG_LOCK`，失败降级提示。

#### 2.3 旅行青蛙动态壁纸（Travel-Frog）

- 动作池：吃饭/写东西/看书/戴帽子/做手工/整理背包/音符。
- 时间调度：根据小时段 + 随机权重切换动作。
- 蜗牛算法修复（每分钟一圈）：

```kotlin
val now = System.currentTimeMillis()
val secInMinute = (now / 1000L) % 60L
val angle = (secInMinute / 60f) * (2f * Math.PI.toFloat())
val x = cx + radius * kotlin.math.cos(angle)
val y = cy + radius * kotlin.math.sin(angle)
```

> 若要“严格每分钟一圈”，按秒推进角度；若要平滑，每帧用当前毫秒计算。

- 屏幕适配：按短边基准缩放角色素材。
- 保留 app 图标元素开关。

#### 2.4 姓氏壁纸（NamePic）

- 单字/姓氏生成：高分辨率 Bitmap（按屏幕密度和分辨率创建）。
- 增强项：字体导入、渐变背景、描边阴影、随机百家姓。
- 导出保存：MediaStore 写入 `Pictures/Wall`。

### 4.3 设置（Settings）

- 保持 Tomato 设置页视觉结构（分组列表 + 次级说明 + 开关/跳转）。
- 核心项：
  - 全局动态壁纸总开关
  - 全局默认刷新间隔
  - 主题（动态色/自定义）
  - 关于/开源许可

---

## 5. 后台任务与更新策略

- 使用 WorkManager 周期任务（>=15 分钟）。
- Worker 流程：
  1. 读取启用规则
  2. 判断是否到达更新窗口
  3. 解析 URL 并下载图片
  4. 设置系统/锁屏壁纸
  5. 回写 lastUpdateTime / lastError

- 避免重复任务：`ExistingPeriodicWorkPolicy.KEEP`。

---

## 6. 权限与兼容

```xml
<uses-permission android:name="android.permission.SET_WALLPAPER" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

动态壁纸服务声明（示例）：

```xml
<service
    android:name=".wallpaper.service.HydrogenWallpaperService"
    android:permission="android.permission.BIND_WALLPAPER"
    android:exported="true">
    <intent-filter>
        <action android:name="android.service.wallpaper.WallpaperService" />
    </intent-filter>
    <meta-data
        android:name="android.service.wallpaper"
        android:resource="@xml/hydrogen_wallpaper" />
</service>
```

---

## 7. 里程碑（建议 4 Sprint）

1. **Sprint 1**：Compose 三页框架 + Tomato 风格 Settings + 主题系统。
2. **Sprint 2**：文字壁纸 + 姓氏壁纸（共享渲染引擎）。
3. **Sprint 3**：首页规则系统（DataStore + 解析 + Worker + 错误展示）。
4. **Sprint 4**：两种动态壁纸（氢 / 旅行青蛙）+ 参数面板 + 性能优化。

---

## 8. 风险与规避

- 第三方源规则失效频繁：必须内置“测试规则/错误提示/手动刷新”。
- 锁屏设置机型兼容问题：明确降级与用户提示。
- 动态壁纸耗电：降低后台帧率，非可见状态停绘。
- 大图内存：统一走采样解码、Bitmap 池与缓存上限。

---

## 9. 验收标准

- 三标签可用，设置页风格与 Tomato 接近。
- 工具页四项功能均可进入并完成最小闭环。
- 首页规则可新增、测试、保存、自动更新并可手动应用。
- Android 8–14 主流机型可设置桌面壁纸；锁屏失败可友好提示。
