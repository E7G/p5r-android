# P5R Android · Launcher Rework 简体中文版

[![Release](https://img.shields.io/github/v/release/E7G/p5r-android?include_prereleases&label=Release)](https://github.com/E7G/p5r-android/releases)
[![构建状态](https://github.com/E7G/p5r-android/actions/workflows/release-zh-cn.yml/badge.svg)](https://github.com/E7G/p5r-android/actions/workflows/release-zh-cn.yml)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

《女神异闻录 5 皇家版》（Persona 5 Royal）的 Android 原生运行项目，基于 [Chakeyy/p5r-android](https://github.com/Chakeyy/p5r-android) 的 Launcher Rework 修复版进行启动器简体中文本地化。

**本项目不是 P5R-Android-Plus，也没有引入 Plus 的渲染代码。**

## 下载

前往 [GitHub Releases](https://github.com/E7G/p5r-android/releases) 下载汉化 APK。

| 项目 | 说明 |
| --- | --- |
| 基础版本 | 0.5.13-launcher-a20-fix2 |
| 上游分支 | build/fix-pass |
| 上游提交 | [081c143](https://github.com/Chakeyy/p5r-android/commit/081c1437ebec3bef6f3114dd389d61491dd37880) |
| 汉化分支 | zh-CN-launcher-fix2 |
| 包名 | io.github.p5rrecomp.opt |
| 架构 | ARM64-v8a |
| 最低系统 | Android 10（API 29） |

每个汉化 Release 由 GitHub Actions 从源码构建，包含 APK 和 SHA256 校验文件。构建进度可在 [Actions](https://github.com/E7G/p5r-android/actions/workflows/release-zh-cn.yml) 查看。

> [!NOTE]
> Release 使用本地或 CI 生成的开发签名，而非上游官方签名。不同签名的版本可能无法覆盖安装，请提前备份存档。CI 未配置固定签名时，不同发布版本之间也可能需要重新安装。

## 汉化内容

- 启动器默认使用简体中文，不依赖系统语言。
- 新增 191 条中文翻译映射，覆盖主页、游戏文件选择、准备流程、显示、性能、控制及外观设置。
- 汉化 GPU 驱动管理、存档导入导出、CPK 模组导入和日志导出等界面。
- 保留 Launcher Rework 的动态背景、界面音效及触觉反馈。
- 保留上游运行逻辑与默认性能参数，修复 Windows 构建时 D8 命令行过长问题。

汉化针对启动器及相关 Android 界面，不包含游戏剧情或游戏资源汉化。尚未完成全部界面的真机核验，部分底层错误信息可能仍显示英文。

## 系统要求

- Android 10 或更新版本，ARM64 处理器，支持 Vulkan 1.1。
- 建议至少 6 GB RAM，8 GB 或以上更佳。
- 预留游戏文件所需空间（约 15 GB），以及约 1 GB 的着色器缓存和存档空间。
- 自行合法取得的 Nintendo Switch 版 Persona 5 Royal **1.0.0 本体**及自己的 prod.keys。

## 安装与使用

1. 从 Releases 下载 APK 并安装。
2. 打开启动器，按提示授予所有文件访问权限。
3. 在文件设置中选择自己的 NSP/XCI 游戏文件和 prod.keys（如需 title.keys，也可一并选择）。
4. 点击「准备游戏」，保持应用开启，等待首次着色器转换完成。
5. 点击「开始游戏」。后续运行无需重复准备，勿移动或删除游戏文件。

游戏文本语言可在启动器设置中选择，游戏语音语言在游戏内设置。

存档路径：`内部存储/Persona 5 Royal/user/save`。可使用启动器的存档导入和导出功能进行备份。

LayeredFS 模组可放置于 `内部存储/Persona 5 Royal/mods/<模组名称>/romfs/`，详见 [模组文档](docs/MODS.md)。

## 兼容性说明

本 fork 保留 Chakeyy 原版 Launcher Rework 的 GPU 与运行逻辑，不承诺所有骁龙、联发科或其他设备均可稳定运行。当前汉化版本已完成本地 Release 编译与 APK 签名验证，尚未完成真机兼容性测试。

遇到闪退或画面异常，请通过启动器导出日志，并在 [Issues](https://github.com/E7G/p5r-android/issues) 提供手机型号、SoC/GPU、Android 版本、内存、复现步骤及相关日志。不要附带游戏文件、密钥或其他隐私信息。

## 构建

本项目采用 NCE（Native Code Execution）方式运行，不依赖 Gradle。

构建环境包括 Android SDK 35、Build Tools 35.0.0、NDK 28.2.13676358、JDK 17+、CMake 3.25+、Ninja、Vulkan Headers 1.4.357，以及用于构建着色器库的 Linux/Docker .NET 10 NativeAOT 环境。

- 自动构建和发布：[release-zh-cn.yml](.github/workflows/release-zh-cn.yml)
- Linux 构建脚本：[build_android.sh](games/p5r/tools/build_android.sh)
- Windows 构建脚本：[build_apk.ps1](android/build_apk.ps1)
- 着色器转换：[相关技术文档](docs/nvn/TRANSLATOR.md)

完成依赖准备后，在 Windows PowerShell 下执行：

```powershell
.\android\build_apk.ps1 -Game p5r -Nce -Release
```

输出路径：`private/p5r/apk_nce/p5r.apk`。

## 致谢与许可

- [Chakeyy/p5r-android](https://github.com/Chakeyy/p5r-android)：Launcher Rework 及本 fork 的基础代码。
- [Brandon04Gonzalez](https://github.com/Brandon04Gonzalez)：Persona 5 Royal Android 移植项目。
- [BelmanteGu / SwitchGamesRecomp](https://github.com/BelmanteGu/SuperMarioWonderRecomp)：Switch 运行时基础。
- [Ryujinx / Ryubing](https://github.com/Ryubing/Ryujinx)：着色器转换器。
- [E7G](https://github.com/E7G)：本 fork 的简体中文本地化与构建发布维护。

原项目社区：[Discord](https://discord.gg/rMgRneBTCe)（英语、西班牙语）；自愿支持原开发者：[GitHub Sponsors](https://github.com/sponsors/Brandon04Gonzalez)。

保留原项目 MIT 许可证：[LICENSE](LICENSE)；第三方许可详见 [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md)。

> [!IMPORTANT]
> 本仓库及 APK 不提供游戏本体、游戏素材、原版着色器、游戏密钥或固件。请使用自己合法拥有的游戏副本及密钥。
>
> Persona 5 Royal 的相关权利归 ATLUS / SEGA 所有。本项目是非官方爱好者项目，与 ATLUS、SEGA 和 Nintendo 无隶属或授权关系。
