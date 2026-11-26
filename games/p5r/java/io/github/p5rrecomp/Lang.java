package io.github.p5rrecomp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The launcher's texts in the device's language. Callers keep their Spanish and English pair (t(es, en)); the other
 * languages are looked up here by the English text, which falls back to English when a text has no translation.
 * Texts with a variable part take it through String.format (%s, %d), so the key stays the same.
 * Only the launcher: the game picks its own text language (the "language" key of config.txt, or the device's).
 */
final class Lang {
    // The columns of the table, after the English key.
    private static final String[] COLUMNS = {"fr", "de", "it", "pt", "ja", "ko", "zh-Hans", "zh-Hant"};
    private static final Map<String, String[]> TABLE = new HashMap<>();
    private static final Map<String, String> ZH = new HashMap<>();
    private static final String DEVICE = "zh-Hans";

    private Lang() {}

    /** The device's language as one of es, en and the table's columns (anything else: en). */
    static String device() {
        Locale l = Locale.getDefault();
        String lang = l.getLanguage();
        if (lang.equals("zh")) {
            String script = l.getScript(), country = l.getCountry();
            boolean traditional = script.equals("Hant")
                    || (script.isEmpty() && (country.equals("TW") || country.equals("HK") || country.equals("MO")));
            return traditional ? "zh-Hant" : "zh-Hans";
        }
        if (lang.equals("es") || lang.equals("en")) return lang;
        for (String c : COLUMNS)
            if (c.equals(lang)) return c;
        return "en";
    }

    static String t(String spanish, String english) {
        if (DEVICE.equals("es")) return spanish;
        if (DEVICE.equals("en")) return english;
        if (DEVICE.equals("zh-Hans") && ZH.containsKey(english)) return ZH.get(english);
        String[] row = TABLE.get(english);
        if (row == null) return english;
        for (int k = 0; k < COLUMNS.length; k++)
            if (COLUMNS[k].equals(DEVICE)) return row[k];
        return english;
    }

    static String f(String spanish, String english, Object... args) {
        return String.format(Locale.getDefault(), t(spanish, english), args);
    }

    private static void put(String english, String... others) {
        if (others.length != COLUMNS.length) throw new IllegalArgumentException(english);
        TABLE.put(english, others);
    }

    static {
        ZH.put("3D Resolution", "3D 分辨率");
        ZH.put("3D scene render scale (UI stays crisp)", "3D 场景渲染比例（界面保持清晰）");
        ZH.put("Interface Resolution", "界面分辨率");
        ZH.put("720p is faster; 1080p renders sharper menus and portraits", "720p 性能更好；1080p 菜单和立绘更清晰");
        ZH.put("Sharpening & Upscaling", "锐化与超分辨率");
        ZH.put("CAS is fast & ring-free; FSR 1.0 upscales to native display", "CAS 快速且无振铃；FSR 1.0 放大至屏幕原生分辨率");
        ZH.put("Sharpening Strength", "锐化强度");
        ZH.put("Tunes CAS or FSR 1.0 sharpening intensity when enabled", "启用时调整 CAS 或 FSR 1.0 的锐化强度");
        ZH.put("SUBTLE", "轻微");
        ZH.put("STRONG", "强");
        ZH.put("Anti-Aliasing (FXAA)", "抗锯齿（FXAA）");
        ZH.put("Smooths 3D stair-stepping jaggies with low GPU overhead", "以较低 GPU 开销平滑 3D 锯齿");
        ZH.put("Color Profile", "色彩配置");
        ZH.put("Zero-cost vibrance and contrast punch in the present pass", "在最终输出阶段增强鲜艳度和对比度");
        ZH.put("DEFAULT", "默认");
        ZH.put("VIVID", "鲜艳");
        ZH.put("True Widescreen (Hor+)", "真宽屏（Hor+）");
        ZH.put("Expands horizontal 3D FOV to fill 20:9 / 21:9 displays", "扩大水平 3D 视野，适配 20:9 / 21:9 屏幕");
        ZH.put("Hor+ UI Framing", "Hor+ 界面比例");
        ZH.put("Keep UI undistorted 16:9 centered or stretch edge-to-edge", "保持 16:9 界面居中不变形，或拉伸至屏幕边缘");
        ZH.put("FULL", "全屏");
        ZH.put("Fullscreen Stretch", "全屏拉伸");
        ZH.put("Stretches 16:9 output to screen edges (disabled when Hor+ is on)", "将 16:9 画面拉伸至屏幕边缘（开启 Hor+ 时禁用）");
        ZH.put("Text & Subtitle Language", "文本与字幕语言");
        ZH.put("In-game text language (voice track is chosen in-game)", "游戏文本语言（语音在游戏内选择）");
        ZH.put("Automatic", "自动");
        ZH.put("Frame Rate Cap", "帧率上限");
        ZH.put("30 fps target for stability; 60 fps is experimental", "30 FPS 更稳定；60 FPS 为实验性功能");
        ZH.put("Async Replay Thread", "异步回放线程");
        ZH.put("Overlaps command recording and Vulkan replay across Big cores", "在大核上并行执行命令记录与 Vulkan 回放");
        ZH.put("Shader Compilation", "着色器编译");
        ZH.put("Async avoids hitches (surfaces pop in); Sync compiles on load (no white pop-in)", "异步减少卡顿（可能出现纹理延迟）；同步在加载时编译（避免白色纹理）");
        ZH.put("ASYNC", "异步");
        ZH.put("SYNC", "同步");
        ZH.put("Texture Quality", "纹理质量");
        ZH.put("Texture mipmap LOD bias (Sharp uses -0.5 for crisper detail)", "纹理 Mipmap LOD 偏移（锐利使用 -0.5 增强细节）");
        ZH.put("LOW", "低");
        ZH.put("HIGH", "高");
        ZH.put("SHARP", "锐利");
        ZH.put("Anisotropic Filtering", "各向异性过滤");
        ZH.put("Sharpens angled floors, streets & walls (4X is nearly free)", "增强倾斜地面、街道和墙壁的清晰度（4X 开销很低）");
        ZH.put("AUTO", "自动");
        ZH.put("Dynamic Shadows", "动态阴影");
        ZH.put("Disabling skips shadow map passes (~9% GPU savings)", "关闭后跳过阴影贴图渲染（约节省 9% GPU 开销）");
        ZH.put("Shadow Map Scale", "阴影贴图比例");
        ZH.put("0.5x half-resolution shadows when shadows are enabled", "启用阴影时，0.5X 使用半分辨率阴影");
        ZH.put("Bloom Post-Processing", "泛光后处理");
        ZH.put("Disabling skips 12 HDR bloom blur passes per frame", "关闭后每帧跳过 12 次 HDR 泛光模糊处理");
        ZH.put("Crowd NPCs", "背景人群 NPC");
        ZH.put("Disabling removes background crowds for higher FPS in cities", "关闭背景人群以提高城市场景帧率");
        ZH.put("FPS Counter", "帧率显示");
        ZH.put("Displays live frame rate overlay while playing", "游戏时实时显示帧率");
        ZH.put("Debug Logging (nx.log)", "调试日志（nx.log）");
        ZH.put("Records engine & GPU details to nx.log (crashes are always logged automatically)", "将引擎和 GPU 详细信息写入 nx.log（崩溃始终自动记录）");
        ZH.put("On-Screen Controls", "屏幕虚拟按键");
        ZH.put("Enable or disable the virtual touchscreen overlay", "启用或关闭屏幕虚拟按键");
        ZH.put("Hide Touch Controls", "隐藏触屏按键");
        ZH.put("Keep touch controls invisible while still accepting touches", "隐藏按键图形，但保留触摸输入");
        ZH.put("Button Size", "按键大小");
        ZH.put("Scale of virtual touchscreen buttons", "屏幕虚拟按键缩放比例");
        ZH.put("SMALL", "小");
        ZH.put("NORMAL", "标准");
        ZH.put("LARGE", "大");
        ZH.put("Button Opacity", "按键不透明度");
        ZH.put("Transparency of on-screen touch controls", "屏幕触控按键的透明程度");
        ZH.put("Touch Stick Sensitivity", "触屏摇杆灵敏度");
        ZH.put("Response multiplier for the virtual left analog stick", "虚拟左摇杆响应倍率");
        ZH.put("Touch Camera Sensitivity", "触屏视角灵敏度");
        ZH.put("Rotation speed for touch camera drag", "触摸拖动时的视角旋转速度");
        ZH.put("Physical Gamepad Layout", "实体手柄布局");
        ZH.put("Face button mapping for Bluetooth / USB controllers", "蓝牙或 USB 手柄的 A/B/X/Y 按键映射");
        ZH.put("NINTENDO", "任天堂");
        ZH.put("XBOX / PC", "Xbox / PC");
        ZH.put("%s\n\nClose the app completely and start it again.", "%s\n\n请完全退出应用后重新启动。");
        ZH.put("ACCESS REQUIRED", "需要授权");
        ZH.put("ADJUST", "调整");
        ZH.put("ANIMATED", "动态");
        ZH.put("APPEARANCE", "外观");
        ZH.put("Accept the prompt to pin the shortcut with your picture to your home screen.", "请确认提示，将使用自定义图片的快捷方式添加到主屏幕。");
        ZH.put("All-files access is required to read your Persona 5 Royal data.", "读取《女神异闻录5 皇家版》数据需要所有文件访问权限。");
        ZH.put("BACK", "返回");
        ZH.put("BTN", "按键");
        ZH.put("Ben-Day dot treatment on the Default Wallpaper", "默认壁纸的本戴网点效果");
        ZH.put("CANCEL", "取消");
        ZH.put("CHECK AGAIN", "重新检查");
        ZH.put("CHOOSE", "选择");
        ZH.put("CHOOSE GAME", "选择游戏");
        ZH.put("CHOOSE KEYS", "选择密钥");
        ZH.put("CONTROLS", "控制");
        ZH.put("CONTROLS LAYOUT", "控制布局");
        ZH.put("Cannot read %s. Please select the file again.", "无法读取 %s，请重新选择文件。");
        ZH.put("Choose your Persona 5 Royal 1.0.0 .nsp / .xci and prod.keys.", "请选择《女神异闻录5 皇家版》1.0.0 的 NSP/XCI 文件和 prod.keys。");
        ZH.put("Console Keys (prod.keys)", "主机密钥（prod.keys）");
        ZH.put("Controls overlay darkness over your custom wallpaper", "调整自定义壁纸遮罩的深浅");
        ZH.put("Could not load that image.", "无法加载该图片。");
        ZH.put("Custom GPU Driver", "自定义 GPU 驱动");
        ZH.put("Custom Home Shortcut Icon", "自定义桌面快捷方式图标");
        ZH.put("Custom image active", "已启用自定义图片");
        ZH.put("DARK", "深色");
        ZH.put("DATA", "数据");
        ZH.put("DISPLAY", "显示");
        ZH.put("Debug Logs", "调试日志");
        ZH.put("Default Wallpaper", "默认壁纸");
        ZH.put("Default Wallpaper Motion", "默认壁纸动态效果");
        ZH.put("Default wallpaper restored", "已恢复默认壁纸");
        ZH.put("Drag and position virtual buttons directly on screen", "在屏幕上直接拖动虚拟按键调整位置");
        ZH.put("Drag controls where you want them.\nD-pad and A B X Y move together. Red: overlapping.", "将按键拖到所需位置。\n方向键与 A B X Y 分组移动。红色表示重叠。");
        ZH.put("Driver import failed", "驱动导入失败");
        ZH.put("Driver imported", "驱动已导入");
        ZH.put("EDIT LAYOUT", "编辑布局");
        ZH.put("ENGINE", "引擎");
        ZH.put("ENHANCE", "增强");
        ZH.put("EXPORT", "导出");
        ZH.put("EXPORT LOGS", "导出日志");
        ZH.put("Exports device info, settings, crash log, nx.log & logcat to Download/p5r-log.txt", "将设备信息、设置、崩溃日志、nx.log 和 logcat 导出至 Download/p5r-log.txt");
        ZH.put("FILES", "文件");
        ZH.put("FOLDER", "文件夹");
        ZH.put("FRAMING", "画面比例");
        ZH.put("GAME FILE UNREADABLE", "无法读取游戏文件");
        ZH.put("GAMEPAD", "手柄");
        ZH.put("GRANT ACCESS", "授予权限");
        ZH.put("Game File (.nsp / .xci)", "游戏文件（NSP / XCI）");
        ZH.put("Game: %s. One-time shader translation is needed.", "游戏：%s。首次运行需要转换着色器。");
        ZH.put("HOME", "主页");
        ZH.put("Halftone Style", "半色调样式");
        ZH.put("IMPORT", "导入");
        ZH.put("IMPORT CPK", "导入 CPK");
        ZH.put("Import Saves", "导入存档");
        ZH.put("Import ZIP/folder (JKSV / Checkpoint) or export backup to Download", "导入 ZIP 或文件夹（JKSV / Checkpoint），或将备份导出到 Download");
        ZH.put("Installs a PATCH1.CPK mod into mods/translation/", "将 PATCH1.CPK 模组安装到 mods/translation/");
        ZH.put("LIGHT", "浅色");
        ZH.put("LOGS", "日志");
        ZH.put("Launcher Haptics", "启动器触觉反馈");
        ZH.put("Launcher Sound Effects", "启动器音效");
        ZH.put("Launcher Wallpaper", "启动器壁纸");
        ZH.put("Logs exported to Download/p5r-log.txt", "日志已导出至 Download/p5r-log.txt");
        ZH.put("MED", "中");
        ZH.put("MENU", "菜单");
        ZH.put("MISSING PROD.KEYS", "缺少 PROD.KEYS");
        ZH.put("NO GAME DATA", "无游戏数据");
        ZH.put("No .so file inside", "未找到 .so 文件");
        ZH.put("No Persona 5 Royal saves found there: look for DATA01, DATA02... or SYSTEM.", "未找到游戏存档，请查找 DATA01、DATA02… 或 SYSTEM 文件夹。");
        ZH.put("No game file selected", "未选择游戏文件");
        ZH.put("OFF", "关闭");
        ZH.put("ON", "开启");
        ZH.put("OPEN", "打开");
        ZH.put("On-Screen Controls Layout", "屏幕按键布局");
        ZH.put("P5-style UI clicks, tab swishes, and launch slash audio", "P5 风格的点击、标签切换及启动音效");
        ZH.put("PERFORMANCE", "性能");
        ZH.put("PLAY", "开始游戏");
        ZH.put("PLAY  ▶", "开始游戏  ▶");
        ZH.put("PREPARATION NEEDED", "需要准备");
        ZH.put("PREPARE GAME", "准备游戏");
        ZH.put("PREPARING THE GAME", "正在准备游戏");
        ZH.put("Persona 5 Royal 1.0.0 base dump", "《女神异闻录5 皇家版》1.0.0 原版数据");
        ZH.put("Pick a ZIP file or folder with Persona 5 Royal saves (DATA01, DATA02... and SYSTEM). Your current saves are backed up first.", "请选择包含游戏存档（DATA01、DATA02… 和 SYSTEM）的 ZIP 或文件夹。现有存档会先进行备份。");
        ZH.put("Pins a home screen shortcut using your own picture", "使用自定义图片创建桌面快捷方式");
        ZH.put("QUALITY", "画质");
        ZH.put("READY TO PLAY", "可以开始游戏");
        ZH.put("REMOVE", "移除");
        ZH.put("RESET", "重置");
        ZH.put("RIBBONS", "飘带");
        ZH.put("Required to decrypt .nsp / .xci files", "用于解密 NSP/XCI 文件");
        ZH.put("SAVE", "保存");
        ZH.put("SCALING", "缩放");
        ZH.put("SELECT", "选择");
        ZH.put("SHADERS · ONE-TIME", "着色器 · 仅首次");
        ZH.put("STAR HALO", "星形光环");
        ZH.put("STATIC", "静态");
        ZH.put("Save Data", "存档数据");
        ZH.put("Saved to Download/%s. Use Import to restore.", "已保存至 Download/%s，可通过导入恢复。");
        ZH.put("Select your console's prod.keys file to decrypt the game.", "请选择主机的 prod.keys 文件以解密游戏。");
        ZH.put("Share log file", "分享日志文件");
        ZH.put("Static freezes the default backdrop; Animated enables ribbons & gyro", "静态模式冻结默认背景；动态模式启用飘带和陀螺仪效果");
        ZH.put("Stick", "摇杆");
        ZH.put("System Driver", "系统驱动");
        ZH.put("System driver", "系统驱动");
        ZH.put("System driver restored", "已恢复系统驱动");
        ZH.put("TABS", "标签页");
        ZH.put("TOUCH", "触屏");
        ZH.put("Tactile vibration feedback on tabs, toggles, and PLAY", "标签、开关和开始游戏按钮的振动反馈");
        ZH.put("Translation / CPK Mod", "汉化 / CPK 模组");
        ZH.put("Translation installed to mods/translation/ (PATCH1.CPK)", "汉化已安装至 mods/translation/（PATCH1.CPK）");
        ZH.put("UNSUPPORTED BUILD", "不支持的游戏版本");
        ZH.put("Wallpaper", "壁纸");
        ZH.put("Wallpaper Dim", "壁纸暗度");
        ZH.put("Wallpaper applied", "壁纸已应用");
        ZH.put("ZIP FILE", "ZIP 文件");
        ZH.put("exefs/main build %s is not base P5R 1.0.0.", "exefs/main 的构建版本 %s 并非 P5R 1.0.0 原版。");
        ZH.put("prod.keys installed", "prod.keys 已安装");
        ZH.put("prod.keys not installed", "prod.keys 未安装");
        // Options
        put("Text and subtitle language", "Langue des textes et sous-titres", "Sprache für Texte und Untertitel",
                "Lingua di testi e sottotitoli", "Idioma dos textos e legendas", "テキストと字幕の言語", "텍스트 및 자막 언어",
                "文本和字幕语言", "文字與字幕語言");
        put("Automatic (device language)", "Automatique (langue de l'appareil)", "Automatisch (Gerätesprache)",
                "Automatica (lingua del dispositivo)", "Automático (idioma do dispositivo)", "自動（端末の言語）",
                "자동 (기기 언어)", "自动（设备语言）", "自動（裝置語言）");
        put("Resolution", "Résolution", "Auflösung", "Risoluzione", "Resolução", "解像度", "해상도", "分辨率", "解析度");
        put("Interface", "Interface", "Benutzeroberfläche", "Interfaccia", "Interface", "インターフェース", "인터페이스",
                "界面", "介面");
        put("720p (faster)", "720p (plus rapide)", "720p (schneller)", "720p (più veloce)", "720p (mais rápida)",
                "720p（高速）", "720p (더 빠름)", "720p（更快）", "720p（較快）");
        put("1080p (sharp text)", "1080p (texte net)", "1080p (scharfer Text)", "1080p (testo nitido)",
                "1080p (texto nítido)", "1080p（くっきりした文字）", "1080p (선명한 텍스트)", "1080p（文字清晰）",
                "1080p（文字清晰）");
        put("Graphics", "Graphismes", "Grafik", "Grafica", "Gráficos", "グラフィック", "그래픽", "画质", "畫質");
        put("Low", "Bas", "Niedrig", "Bassa", "Baixo", "低", "낮음", "低", "低");
        put("Medium", "Moyens", "Mittel", "Media", "Médio", "中", "중간", "中", "中");
        put("High", "Élevés", "Hoch", "Alta", "Alto", "高", "높음", "高", "高");
        put("Frame rate", "Images par seconde", "Bildrate", "Fotogrammi al secondo", "Quadros por segundo",
                "フレームレート", "프레임 레이트", "帧率", "影格率");
        put("60 fps (experimental)", "60 fps (expérimental)", "60 fps (experimentell)", "60 fps (sperimentale)",
                "60 fps (experimental)", "60 fps（実験的）", "60 fps (실험적)", "60 fps（实验性）", "60 fps（實驗性）");
        put("Shadows", "Ombres", "Schatten", "Ombre", "Sombras", "影", "그림자", "阴影", "陰影");
        put("Shadow quality", "Qualité des ombres", "Schattenqualität", "Qualità delle ombre", "Qualidade das sombras",
                "影の品質", "그림자 품질", "阴影质量", "陰影品質");
        put("Low (0.5x - best performance)", "Basse (0.5x - meilleures performances)", "Niedrig (0.5x - beste Leistung)",
                "Bassa (0.5x - prestazioni migliori)", "Baixa (0.5x - melhor desempenho)", "低 (0.5x - 高性能)",
                "낮음 (0.5x - 최고 성능)", "低（0.5x - 最佳性能）", "低（0.5x - 最佳效能）");
        put("Original (1.0x)", "Originale (1.0x)", "Original (1.0x)", "Originale (1.0x)", "Original (1.0x)",
                "オリジナル (1.0x)", "원본 (1.0x)", "原始（1.0x）", "原始（1.0x）");
        put("Off", "Désactivé", "Aus", "Disattivato", "Desativado", "オフ", "끔", "关", "關");
        put("Fullscreen (stretch)", "Plein écran (étirer)", "Vollbild (strecken)", "Schermo intero (allarga)", "Tela cheia (esticar)", "\u5168\u753b\u9762\uff08\u5f15\u304d\u4f38\u3070\u3057\uff09", "\uc804\uccb4 \ud654\uba74 (\ub298\uc774\uae30)", "\u5168\u5c4f\uff08\u62c9\u4f38\uff09", "\u5168\u87a2\u5e55\uff08\u62c9\u4f38\uff09");
        put("Super resolution (FSR 1.0)", "Super-résolution (FSR 1.0)", "Superauflösung (FSR 1.0)",
                "Super risoluzione (FSR 1.0)", "Super resolução (FSR 1.0)", "超解像（FSR 1.0）",
                "초해상도 (FSR 1.0)", "超分辨率（FSR 1.0）", "超解析度（FSR 1.0）");
        put("On", "Activé", "An", "Attivato", "Ativado", "オン", "켬", "开", "開");
        put("FPS counter", "Compteur de FPS", "FPS-Anzeige", "Contatore FPS", "Contador de FPS", "FPS表示", "FPS 표시",
                "帧率显示", "FPS 顯示");
        put("On-screen controls", "Commandes tactiles", "Touch-Steuerung", "Comandi touch", "Controles na tela",
                "タッチ操作", "터치 컨트롤", "触屏按键", "觸控按鍵");
        put("Button size", "Taille des boutons", "Tastengröße", "Dimensione dei pulsanti", "Tamanho dos botões",
                "ボタンの大きさ", "버튼 크기", "按键大小", "按鍵大小");
        put("Small", "Petite", "Klein", "Piccola", "Pequeno", "小", "작게", "小", "小");
        put("Normal", "Normale", "Normal", "Normale", "Normal", "標準", "보통", "标准", "標準");
        put("Large", "Grande", "Groß", "Grande", "Grande", "大", "크게", "大", "大");
        put("Button opacity", "Opacité des boutons", "Deckkraft der Tasten", "Opacità dei pulsanti",
                "Opacidade dos botões", "ボタンの不透明度", "버튼 불투명도", "按键不透明度", "按鍵不透明度");
        put("Controls position", "Position des commandes", "Position der Steuerung", "Posizione dei comandi",
                "Posição dos controles", "操作ボタンの配置", "컨트롤 위치", "按键位置", "按鍵位置");
        put("Move", "Déplacer", "Verschieben", "Sposta", "Mover", "移動", "이동", "移动", "移動");
        put("Saves", "Sauvegardes", "Spielstände", "Salvataggi", "Jogos salvos", "セーブデータ", "저장 데이터", "存档",
                "存檔");
        put("Import", "Importer", "Importieren", "Importa", "Importar", "インポート", "가져오기", "导入", "匯入");
        put("Export", "Exporter", "Exportieren", "Esporta", "Exportar", "エクスポート", "내보내기", "导出", "匯出");
        // Status and the play button
        put("All-files access is needed to read the game data.",
                "L'accès à tous les fichiers est nécessaire pour lire les données du jeu.",
                "Für die Spieldaten wird Zugriff auf alle Dateien benötigt.",
                "Serve l'accesso a tutti i file per leggere i dati del gioco.",
                "É preciso o acesso a todos os arquivos para ler os dados do jogo.",
                "ゲームデータを読み込むには、すべてのファイルへのアクセス許可が必要です。",
                "게임 데이터를 읽으려면 모든 파일 액세스 권한이 필요합니다.",
                "需要“所有文件访问权限”才能读取游戏数据。",
                "需要「所有檔案存取權」才能讀取遊戲資料。");
        put("Grant access", "Autoriser l'accès", "Zugriff erlauben", "Concedi l'accesso", "Permitir acesso",
                "アクセスを許可", "액세스 허용", "授予权限", "授予權限");
        put("No game data found in Internal storage/%s (exefs, extract, spirv) from your own copy of the game.",
                "Aucune donnée de jeu dans Stockage interne/%s (exefs, extract, spirv) issue de votre propre copie du jeu.",
                "Keine Spieldaten in Interner Speicher/%s (exefs, extract, spirv) aus deiner eigenen Kopie des Spiels.",
                "Nessun dato di gioco in Memoria interna/%s (exefs, extract, spirv) dalla tua copia del gioco.",
                "Nenhum dado do jogo em Armazenamento interno/%s (exefs, extract, spirv) da sua própria cópia do jogo.",
                "内部ストレージ/%s に、ご自身のゲームから用意したデータ（exefs、extract、spirv）が見つかりません。",
                "내부 저장소/%s 에서 본인 소유 게임의 데이터(exefs, extract, spirv)를 찾을 수 없습니다.",
                "在 内部存储/%s 中找不到从你自己的游戏副本准备的数据（exefs、extract、spirv）。",
                "在 內部儲存空間/%s 中找不到從你自己的遊戲副本準備的資料（exefs、extract、spirv）。");
        put("Internal storage/%s/exefs/main is not Persona 5 Royal 1.0.0 (build %s). Copy the exefs of the base game, without the update.",
                "Stockage interne/%s/exefs/main n'est pas Persona 5 Royal 1.0.0 (build %s). Copiez l'exefs du jeu de base, sans la mise à jour.",
                "Interner Speicher/%s/exefs/main ist nicht Persona 5 Royal 1.0.0 (Build %s). Kopiere das exefs des Grundspiels, ohne das Update.",
                "Memoria interna/%s/exefs/main non è Persona 5 Royal 1.0.0 (build %s). Copia l'exefs del gioco base, senza l'aggiornamento.",
                "Armazenamento interno/%s/exefs/main não é Persona 5 Royal 1.0.0 (build %s). Copie o exefs do jogo base, sem a atualização.",
                "内部ストレージ/%s/exefs/main は Persona 5 Royal 1.0.0 ではありません（ビルド %s）。アップデートを含まない本体の exefs をコピーしてください。",
                "내부 저장소/%s/exefs/main 은(는) Persona 5 Royal 1.0.0 이 아닙니다(빌드 %s). 업데이트가 없는 기본 게임의 exefs 를 복사하세요.",
                "内部存储/%s/exefs/main 不是 Persona 5 Royal 1.0.0（版本 %s）。请复制不含更新的本体游戏的 exefs。",
                "內部儲存空間/%s/exefs/main 不是 Persona 5 Royal 1.0.0（版本 %s）。請複製不含更新的本體遊戲的 exefs。");
        put("Look again", "Chercher à nouveau", "Erneut suchen", "Cerca di nuovo", "Procurar de novo", "再検索",
                "다시 찾기", "重新查找", "重新尋找");
        put("Data ready: %s", "Données prêtes : %s", "Daten bereit: %s", "Dati pronti: %s", "Dados prontos: %s",
                "データ準備完了：%s", "데이터 준비됨: %s", "数据就绪：%s", "資料就緒：%s");
        put("Play", "Jouer", "Spielen", "Gioca", "Jogar", "プレイ", "플레이", "开始游戏", "開始遊戲");
        // Saves
        put("Import saves", "Importer des sauvegardes", "Spielstände importieren", "Importa salvataggi",
                "Importar jogos salvos", "セーブデータのインポート", "저장 데이터 가져오기", "导入存档", "匯入存檔");
        put("Pick a ZIP file or a folder with Persona 5 Royal saves (the DATA01, DATA02... and SYSTEM folders, as JKSV or "
                        + "Checkpoint export them, or the ZIP the Export button makes). Your current saves are copied "
                        + "aside first.",
                "Choisissez un fichier ZIP ou un dossier avec des sauvegardes de Persona 5 Royal (les dossiers DATA01, "
                        + "DATA02... et SYSTEM, tels que JKSV ou Checkpoint les exportent, ou le ZIP créé par le bouton "
                        + "Exporter). Vos sauvegardes actuelles sont copiées à part avant.",
                "Wähle eine ZIP-Datei oder einen Ordner mit Spielständen von Persona 5 Royal (die Ordner DATA01, "
                        + "DATA02... und SYSTEM, wie JKSV oder Checkpoint sie exportieren, oder die ZIP-Datei der "
                        + "Exportieren-Taste). Deine aktuellen Spielstände werden vorher gesichert.",
                "Scegli un file ZIP o una cartella con salvataggi di Persona 5 Royal (le cartelle DATA01, DATA02... e "
                        + "SYSTEM, come le esportano JKSV o Checkpoint, o lo ZIP creato dal pulsante Esporta). Prima "
                        + "viene fatta una copia dei salvataggi attuali.",
                "Escolha um arquivo ZIP ou uma pasta com jogos salvos de Persona 5 Royal (as pastas DATA01, DATA02... e "
                        + "SYSTEM, como o JKSV ou o Checkpoint exportam, ou o ZIP que o botão Exportar cria). Antes, é "
                        + "feita uma cópia dos seus jogos salvos atuais.",
                "Persona 5 Royal のセーブデータが入った ZIP ファイルまたはフォルダを選んでください（JKSV や Checkpoint "
                        + "が書き出す DATA01、DATA02…と SYSTEM フォルダ、またはエクスポートボタンで作った ZIP）。"
                        + "先に現在のセーブデータのコピーを取ります。",
                "Persona 5 Royal 저장 데이터가 든 ZIP 파일이나 폴더를 선택하세요 (JKSV나 Checkpoint가 내보내는 DATA01, "
                        + "DATA02... 및 SYSTEM 폴더, 또는 내보내기 버튼으로 만든 ZIP). 먼저 현재 저장 데이터를 따로 "
                        + "복사해 둡니다.",
                "请选择包含 Persona 5 Royal 存档的 ZIP 文件或文件夹（JKSV 或 Checkpoint 导出的 DATA01、DATA02… 和 "
                        + "SYSTEM 文件夹，或“导出”按钮生成的 ZIP）。会先备份你当前的存档。",
                "請選擇包含 Persona 5 Royal 存檔的 ZIP 檔案或資料夾（JKSV 或 Checkpoint 匯出的 DATA01、DATA02… 和 "
                        + "SYSTEM 資料夾，或「匯出」按鈕產生的 ZIP）。會先備份你目前的存檔。");
        put("ZIP file", "Fichier ZIP", "ZIP-Datei", "File ZIP", "Arquivo ZIP", "ZIP ファイル", "ZIP 파일", "ZIP 文件",
                "ZIP 檔案");
        put("Folder", "Dossier", "Ordner", "Cartella", "Pasta", "フォルダ", "폴더", "文件夹", "資料夾");
        put("Cancel", "Annuler", "Abbrechen", "Annulla", "Cancelar", "キャンセル", "취소", "取消", "取消");
        put("No saves found", "Aucune sauvegarde trouvée", "Keine Spielstände gefunden", "Nessun salvataggio trovato",
                "Nenhum jogo salvo encontrado", "セーブデータが見つかりません", "저장 데이터를 찾을 수 없음", "未找到存档",
                "找不到存檔");
        put("No Persona 5 Royal saves there: look for the DATA01, DATA02... or SYSTEM folders.",
                "Aucune sauvegarde de Persona 5 Royal ici : cherchez les dossiers DATA01, DATA02... ou SYSTEM.",
                "Dort sind keine Spielstände von Persona 5 Royal: suche die Ordner DATA01, DATA02... oder SYSTEM.",
                "Lì non ci sono salvataggi di Persona 5 Royal: cerca le cartelle DATA01, DATA02... o SYSTEM.",
                "Não há jogos salvos de Persona 5 Royal aí: procure as pastas DATA01, DATA02... ou SYSTEM.",
                "そこには Persona 5 Royal のセーブデータがありません。DATA01、DATA02…または SYSTEM フォルダを探してください。",
                "그곳에는 Persona 5 Royal 저장 데이터가 없습니다. DATA01, DATA02... 또는 SYSTEM 폴더를 찾으세요.",
                "那里没有 Persona 5 Royal 的存档：请找 DATA01、DATA02… 或 SYSTEM 文件夹。",
                "那裡沒有 Persona 5 Royal 的存檔：請找 DATA01、DATA02… 或 SYSTEM 資料夾。");
        put("system settings only", "réglages système uniquement", "nur Systemeinstellungen",
                "solo impostazioni di sistema", "apenas configurações do sistema", "システム設定のみ", "시스템 설정만",
                "仅系统设置", "僅系統設定");
        put("slots %s", "emplacements %s", "Plätze %s", "slot %s", "espaços %s", "スロット %s", "슬롯 %s", "存档位 %s",
                "存檔位 %s");
        put("\n\nYour previous saves are in user/%s.", "\n\nVos sauvegardes précédentes sont dans user/%s.",
                "\n\nDeine bisherigen Spielstände liegen in user/%s.", "\n\nI salvataggi precedenti sono in user/%s.",
                "\n\nSeus jogos salvos anteriores estão em user/%s.", "\n\n以前のセーブデータは user/%s にあります。",
                "\n\n이전 저장 데이터는 user/%s 에 있습니다.", "\n\n你之前的存档在 user/%s。", "\n\n你之前的存檔在 user/%s。");
        put("Saves imported", "Sauvegardes importées", "Spielstände importiert", "Salvataggi importati",
                "Jogos salvos importados", "セーブデータをインポートしました", "저장 데이터를 가져왔습니다", "存档已导入",
                "存檔已匯入");
        put("%d files (%s). Open them from Load Game.", "%d fichiers (%s). Ouvrez-les depuis Charger.",
                "%d Dateien (%s). Öffne sie über Laden.", "%d file (%s). Aprili da Carica partita.",
                "%d arquivos (%s). Abra-os em Carregar jogo.", "%d 個のファイル（%s）。ロードから開いてください。",
                "파일 %d개 (%s). 불러오기에서 여세요.", "%d 个文件（%s）。请从“读取存档”打开。",
                "%d 個檔案（%s）。請從「讀取存檔」開啟。");
        put("Import failed", "Échec de l'importation", "Import fehlgeschlagen", "Importazione non riuscita",
                "Falha na importação", "インポートできませんでした", "가져오기 실패", "导入失败", "匯入失敗");
        put("No saves", "Aucune sauvegarde", "Keine Spielstände", "Nessun salvataggio", "Nenhum jogo salvo",
                "セーブデータなし", "저장 데이터 없음", "没有存档", "沒有存檔");
        put("There are no saves yet.", "Il n'y a pas encore de sauvegarde.", "Es gibt noch keine Spielstände.",
                "Non ci sono ancora salvataggi.", "Ainda não há jogos salvos.", "まだセーブデータがありません。",
                "아직 저장 데이터가 없습니다.", "还没有存档。", "還沒有存檔。");
        put("Saves exported", "Sauvegardes exportées", "Spielstände exportiert", "Salvataggi esportati",
                "Jogos salvos exportados", "セーブデータをエクスポートしました", "저장 데이터를 내보냈습니다", "存档已导出",
                "存檔已匯出");
        put("Saved to Download/%s. The Import button takes it back as is.",
                "Enregistré dans Download/%s. Le bouton Importer le reprend tel quel.",
                "Gespeichert in Download/%s. Die Importieren-Taste übernimmt die Datei unverändert.",
                "Salvato in Download/%s. Il pulsante Importa lo riprende così com'è.",
                "Salvo em Download/%s. O botão Importar o aceita como está.",
                "Download/%s に保存しました。インポートボタンでそのまま読み込めます。",
                "Download/%s 에 저장했습니다. 가져오기 버튼으로 그대로 불러올 수 있습니다.",
                "已保存到 Download/%s。可用“导入”按钮原样导入。",
                "已儲存到 Download/%s。可用「匯入」按鈕原樣匯入。");
        put("Export failed", "Échec de l'exportation", "Export fehlgeschlagen", "Esportazione non riuscita",
                "Falha na exportação", "エクスポートできませんでした", "내보내기 실패", "导出失败", "匯出失敗");
        // The controls editor
        put("Drag the controls where you want them.\nThe d-pad and A B X Y move together. Red: they touch.",
                "Faites glisser les commandes où vous voulez.\nLa croix et A B X Y bougent ensemble. En rouge : elles se touchent.",
                "Ziehe die Tasten an die gewünschte Stelle.\nSteuerkreuz und A B X Y bewegen sich gemeinsam. Rot: sie berühren sich.",
                "Trascina i comandi dove vuoi.\nLa croce direzionale e A B X Y si muovono insieme. In rosso: si toccano.",
                "Arraste os controles para onde quiser.\nO direcional e A B X Y se movem juntos. Em vermelho: estão se tocando.",
                "操作ボタンを好きな位置へドラッグしてください。\n十字ボタンと A B X Y は一緒に動きます。赤：重なっています。",
                "컨트롤을 원하는 곳으로 끌어 놓으세요.\n방향 버튼과 A B X Y는 함께 움직입니다. 빨간색: 서로 닿음.",
                "把按键拖到你想要的位置。\n十字键和 A B X Y 会一起移动。红色：互相重叠。",
                "把按鍵拖到你想要的位置。\n十字鍵和 A B X Y 會一起移動。紅色：互相重疊。");
        put("Reset", "Réinitialiser", "Zurücksetzen", "Ripristina", "Restaurar", "リセット", "초기화", "重置", "重設");
        put("Save", "Enregistrer", "Speichern", "Salva", "Salvar", "保存", "저장", "保存", "儲存");
        put("Controls saved", "Commandes enregistrées", "Steuerung gespeichert", "Comandi salvati", "Controles salvos",
                "保存しました", "저장했습니다", "已保存", "已儲存");
        put("Could not write config.txt", "Impossible d'écrire config.txt", "config.txt konnte nicht geschrieben werden",
                "Impossibile scrivere config.txt", "Não foi possível gravar config.txt", "config.txt を書き込めませんでした",
                "config.txt 를 쓸 수 없습니다", "无法写入 config.txt", "無法寫入 config.txt");
        // The game file, its preparation and the icon
        put("Game file (.nsp / .xci)", "Fichier du jeu (.nsp / .xci)", "Spieldatei (.nsp / .xci)", "File del gioco (.nsp / .xci)", "Arquivo do jogo (.nsp / .xci)", "ゲームファイル (.nsp / .xci)", "게임 파일 (.nsp / .xci)", "游戏文件 (.nsp / .xci)", "遊戲檔案 (.nsp / .xci)");
        put("Choose", "Choisir", "Auswählen", "Scegli", "Escolher", "選択", "선택", "选择", "選擇");
        put("Keys (prod.keys)", "Clés (prod.keys)", "Schlüssel (prod.keys)", "Chiavi (prod.keys)", "Chaves (prod.keys)", "キー (prod.keys)", "키 (prod.keys)", "密钥 (prod.keys)", "金鑰 (prod.keys)");
        put("Icon", "Icône", "Symbol", "Icona", "Ícone", "アイコン", "아이콘", "图标", "圖示");
        put("Choose a picture", "Choisir une image", "Bild auswählen", "Scegli un'immagine", "Escolher uma imagem", "画像を選択", "이미지 선택", "选择图片", "選擇圖片");
        put("Choose your game file (a Persona 5 Royal 1.0.0 .nsp or .xci) and your prod.keys.", "Choisissez le fichier de votre jeu (un .nsp ou .xci de Persona 5 Royal 1.0.0) et votre prod.keys.", "Wähle deine Spieldatei (eine .nsp oder .xci von Persona 5 Royal 1.0.0) und deine prod.keys.", "Scegli il file del tuo gioco (un .nsp o .xci di Persona 5 Royal 1.0.0) e il tuo prod.keys.", "Escolha o arquivo do seu jogo (um .nsp ou .xci de Persona 5 Royal 1.0.0) e o seu prod.keys.", "ゲームファイル（Persona 5 Royal 1.0.0 の .nsp または .xci）と prod.keys を選んでください。", "게임 파일(Persona 5 Royal 1.0.0 의 .nsp 또는 .xci)과 prod.keys 를 선택하세요.", "请选择你的游戏文件（Persona 5 Royal 1.0.0 的 .nsp 或 .xci）和 prod.keys。", "請選擇你的遊戲檔案（Persona 5 Royal 1.0.0 的 .nsp 或 .xci）和 prod.keys。");
        put("Choose the game", "Choisir le jeu", "Spiel auswählen", "Scegli il gioco", "Escolher o jogo", "ゲームを選択", "게임 선택", "选择游戏", "選擇遊戲");
        put("The game file can no longer be read (%s): choose it again.", "Le fichier du jeu ne peut plus être lu (%s) : choisissez-le à nouveau.", "Die Spieldatei kann nicht mehr gelesen werden (%s): wähle sie erneut aus.", "Il file del gioco non è più leggibile (%s): sceglilo di nuovo.", "O arquivo do jogo não pode mais ser lido (%s): escolha-o de novo.", "ゲームファイル (%s) を読み込めなくなりました。もう一度選んでください。", "게임 파일(%s)을 더 이상 읽을 수 없습니다. 다시 선택하세요.", "无法再读取游戏文件（%s）：请重新选择。", "無法再讀取遊戲檔案（%s）：請重新選擇。");
        put("Your prod.keys file is missing.", "Il manque votre fichier prod.keys.", "Deine prod.keys-Datei fehlt.", "Manca il tuo file prod.keys.", "Falta o seu arquivo prod.keys.", "prod.keys ファイルがありません。", "prod.keys 파일이 없습니다.", "缺少你的 prod.keys 文件。", "缺少你的 prod.keys 檔案。");
        put("Choose prod.keys", "Choisir prod.keys", "prod.keys auswählen", "Scegli prod.keys", "Escolher prod.keys", "prod.keys を選択", "prod.keys 선택", "选择 prod.keys", "選擇 prod.keys");
        put("Game: %s. It needs preparing: once, a few minutes.", "Jeu : %s. Il doit être préparé : une seule fois, quelques minutes.", "Spiel: %s. Es muss vorbereitet werden: einmalig, einige Minuten.", "Gioco: %s. Va preparato: una sola volta, qualche minuto.", "Jogo: %s. Precisa ser preparado: só uma vez, alguns minutos.", "ゲーム：%s。準備が必要です（最初の一度だけ、数分）。", "게임: %s. 준비가 필요합니다(한 번만, 몇 분).", "游戏：%s。需要准备：仅一次，几分钟。", "遊戲：%s。需要準備：僅一次，幾分鐘。");
        put("Prepare the game", "Préparer le jeu", "Spiel vorbereiten", "Prepara il gioco", "Preparar o jogo", "ゲームを準備", "게임 준비", "准备游戏", "準備遊戲");
        put("Game ready: %s", "Jeu prêt : %s", "Spiel bereit: %s", "Gioco pronto: %s", "Jogo pronto: %s", "ゲームの準備完了：%s", "게임 준비됨: %s", "游戏就绪：%s", "遊戲就緒：%s");
        put("Not a game file", "Ce n'est pas un fichier de jeu", "Keine Spieldatei", "Non è un file di gioco", "Não é um arquivo de jogo", "ゲームファイルではありません", "게임 파일이 아닙니다", "不是游戏文件", "不是遊戲檔案");
        put("Choose the base game's .nsp or .xci file (not the update's).", "Choisissez le fichier .nsp ou .xci du jeu de base (pas celui de la mise à jour).", "Wähle die .nsp- oder .xci-Datei des Grundspiels (nicht die des Updates).", "Scegli il file .nsp o .xci del gioco base (non quello dell'aggiornamento).", "Escolha o arquivo .nsp ou .xci do jogo base (não o da atualização).", "本体ゲームの .nsp または .xci を選んでください（アップデートのものではありません）。", "기본 게임의 .nsp 또는 .xci 파일을 선택하세요(업데이트 파일이 아님).", "请选择本体游戏的 .nsp 或 .xci 文件（不是更新文件）。", "請選擇本體遊戲的 .nsp 或 .xci 檔案（不是更新檔案）。");
        put("Not a keys file", "Ce n'est pas un fichier de clés", "Keine Schlüsseldatei", "Non è un file di chiavi", "Não é um arquivo de chaves", "キーファイルではありません", "키 파일이 아닙니다", "不是密钥文件", "不是金鑰檔案");
        put("That file has no keys (header_key). Choose your console's prod.keys.", "Ce fichier ne contient pas les clés (header_key). Choisissez le prod.keys de votre console.", "Diese Datei enthält keine Schlüssel (header_key). Wähle die prod.keys deiner Konsole.", "Quel file non contiene le chiavi (header_key). Scegli il prod.keys della tua console.", "Esse arquivo não tem as chaves (header_key). Escolha o prod.keys do seu console.", "このファイルにはキー（header_key）がありません。お使いの本体の prod.keys を選んでください。", "이 파일에는 키(header_key)가 없습니다. 본체의 prod.keys 를 선택하세요.", "该文件不含密钥（header_key）。请选择你主机的 prod.keys。", "該檔案不含金鑰（header_key）。請選擇你主機的 prod.keys。");
        put("The game could not be opened", "Impossible d'ouvrir le jeu", "Das Spiel konnte nicht geöffnet werden", "Impossibile aprire il gioco", "Não foi possível abrir o jogo", "ゲームを開けませんでした", "게임을 열 수 없습니다", "无法打开游戏", "無法開啟遊戲");
        put("Your home screen does not support shortcuts.", "Votre écran d'accueil ne prend pas en charge les raccourcis.", "Dein Startbildschirm unterstützt keine Verknüpfungen.", "La tua schermata Home non supporta le scorciatoie.", "Sua tela inicial não aceita atalhos.", "ホーム画面がショートカットに対応していません。", "홈 화면이 바로가기를 지원하지 않습니다.", "你的主屏幕不支持快捷方式。", "你的主畫面不支援捷徑。");
        put("Accept the prompt to put the shortcut with your picture on the home screen. The icon in the app drawer stays: Android does not let an app change its own.", "Acceptez la demande pour placer le raccourci avec votre image sur l'écran d'accueil. L'icône du tiroir d'applications ne change pas : Android ne laisse pas une app changer la sienne.", "Bestätige die Anfrage, um die Verknüpfung mit deinem Bild auf den Startbildschirm zu legen. Das Symbol in der App-Übersicht bleibt: Android lässt Apps ihr eigenes nicht ändern.", "Accetta la richiesta per mettere la scorciatoia con la tua immagine nella schermata Home. L'icona nel cassetto delle app resta: Android non lascia che un'app cambi la propria.", "Aceite o aviso para colocar o atalho com sua imagem na tela inicial. O ícone na gaveta de apps continua: o Android não deixa um app mudar o próprio.", "表示される確認を許可すると、画像付きのショートカットがホーム画面に追加されます。アプリ一覧のアイコンは変わりません（Android ではアプリが自分のアイコンを変更できません）。", "확인 창을 수락하면 이미지가 들어간 바로가기가 홈 화면에 추가됩니다. 앱 서랍의 아이콘은 그대로입니다(Android 는 앱이 자신의 아이콘을 바꾸지 못하게 합니다).", "接受提示即可将带有你图片的快捷方式放到主屏幕。应用抽屉中的图标不变：Android 不允许应用更改自己的图标。", "接受提示即可將帶有你圖片的捷徑放到主畫面。應用程式抽屜中的圖示不變：Android 不允許應用程式更改自己的圖示。");
        put("That picture could not be used.", "Impossible d'utiliser cette image.", "Dieses Bild kann nicht verwendet werden.", "Impossibile usare quell'immagine.", "Não foi possível usar essa imagem.", "その画像は使用できませんでした。", "그 이미지를 사용할 수 없습니다.", "无法使用该图片。", "無法使用該圖片。");
        put("Preparing the game", "Préparation du jeu", "Spiel wird vorbereitet", "Preparazione del gioco", "Preparando o jogo", "ゲームを準備中", "게임 준비 중", "正在准备游戏", "正在準備遊戲");
        put("This is needed only once. Keep the app open until it ends (a few minutes).", "Une seule fois suffit. Laissez l'app ouverte jusqu'à la fin (quelques minutes).", "Das ist nur einmal nötig. Lass die App geöffnet, bis es fertig ist (einige Minuten).", "Serve una sola volta. Tieni l'app aperta finché non termina (qualche minuto).", "Só é preciso uma vez. Deixe o app aberto até terminar (alguns minutos).", "最初の一度だけ必要です。終わるまでアプリを開いたままにしてください（数分）。", "한 번만 필요합니다. 끝날 때까지 앱을 열어 두세요(몇 분).", "只需一次。请保持应用打开直到完成（几分钟）。", "只需一次。請保持應用程式開啟直到完成（幾分鐘）。");
        put("Back", "Retour", "Zurück", "Indietro", "Voltar", "戻る", "뒤로", "返回", "返回");
        put("Opening the game…", "Ouverture du jeu…", "Spiel wird geöffnet…", "Apertura del gioco…", "Abrindo o jogo…", "ゲームを開いています…", "게임을 여는 중…", "正在打开游戏…", "正在開啟遊戲…");
        put("Done. You can play now.", "Terminé. Vous pouvez jouer.", "Fertig. Du kannst jetzt spielen.", "Fatto. Ora puoi giocare.", "Pronto. Já pode jogar.", "完了しました。プレイできます。", "완료되었습니다. 이제 플레이할 수 있습니다.", "完成。现在可以玩了。", "完成。現在可以玩了。");
        put("The game could not be prepared: ", "Le jeu n'a pas pu être préparé : ", "Das Spiel konnte nicht vorbereitet werden: ", "Impossibile preparare il gioco: ", "Não foi possível preparar o jogo: ", "ゲームを準備できませんでした：", "게임을 준비할 수 없습니다: ", "无法准备游戏：", "無法準備遊戲：");
        put("Extracting the game's shaders (%d of %d)…", "Extraction des shaders du jeu (%d sur %d)…", "Shader des Spiels werden extrahiert (%d von %d)…", "Estrazione degli shader del gioco (%d di %d)…", "Extraindo os shaders do jogo (%d de %d)…", "ゲームのシェーダーを抽出中（%d / %d）…", "게임 셰이더 추출 중(%d / %d)…", "正在提取游戏着色器（%d / %d）…", "正在擷取遊戲著色器（%d / %d）…");
        put("Translating the shaders (%d of %d)…", "Traduction des shaders (%d sur %d)…", "Shader werden übersetzt (%d von %d)…", "Traduzione degli shader (%d di %d)…", "Traduzindo os shaders (%d de %d)…", "シェーダーを変換中（%d / %d）…", "셰이더 변환 중(%d / %d)…", "正在转换着色器（%d / %d）…", "正在轉換著色器（%d / %d）…");
        put("Finishing…", "Finalisation…", "Wird abgeschlossen…", "Completamento…", "Finalizando…", "仕上げ中…", "마무리 중…", "正在完成…", "正在完成…");
    }
}
