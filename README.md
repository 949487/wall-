# Bizhi 壁纸

基于 **Jetpack Compose + Material 3** 的 Android 壁纸应用，采用多模块架构，支持从可配置的规则源自动获取壁纸，并内置动态壁纸、文字壁纸、姓氏壁纸等工具。

## 功能

- **首页**：壁纸流展示，支持 XPath / JSONPath 规则源管理，按周期自动更新并应用壁纸
- **工具**
  - 氢动态壁纸（粒子背景）
  - 文字壁纸（生成后一键应用到桌面与锁屏）
  - 旅行青蛙动态壁纸（按时段切换动作）
  - 姓氏壁纸（单字生成）
- **设置**：动态取色（Android 12+）与预设配色、全局刷新间隔、规则源管理

## 模块结构

```text
app/               应用入口，底部三栏导航与主题
feature_home/      壁纸流与规则源界面
feature_tools/     工具箱界面
feature_settings/  设置与规则持久化
core_network/      网络请求、HTML 解析、壁纸同步 Worker
core_wallpaper/    动态壁纸服务与壁纸生成
core_xpath/        XPath / JSONPath 规则解析
docs/              技术规划文档
config/            规则源示例
```

## 构建

- JDK 17
- Android Studio（AGP 8.7.3 / Kotlin 2.0.21）
- compileSdk 36、minSdk 28

仓库暂未包含 Gradle Wrapper，直接用 Android Studio 打开根目录同步构建即可。首次运行需在本地生成 `local.properties` 指向你的 SDK 路径（该文件已被忽略，不要提交）。

## 规则源

`config/example_sources.json` 提供了规则源格式示例：`ruleArticles`、`ruleImage`、`ruleTitle`、`sortUrl` 等字段，支持 JSONPath 与 HTML XPath 两种解析方式。

## 许可

[Apache License 2.0](LICENSE)

---

> 本仓库由早期单模块版本与多模块版本合并而来，旧实现仍保留在 Git 历史中（`git log --all` 可查看）。
