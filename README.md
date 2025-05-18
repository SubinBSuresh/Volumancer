# Volumancer
# 🎚️ Volumancer

Volumancer is a minimalist Android utility app (targeting Android 11 and above) that lets you control **media volume** seamlessly using a **home screen widget** and a floating **quick-ball-style UI**.

No bloated UI. No unnecessary permissions. Just elegant control at your fingertips. 🧙‍♂️

---

## 📦 Features

- 📱 **Home screen widget** to instantly raise or lower media volume.
- 💬 **Floating Quick Ball** UI (like AssistiveTouch) to control volume system-wide.
- ⚡ Lightweight, background-service-based architecture.
- ✅ Works on Android 11+ (tested with `targetSdkVersion 34`).
- 🔒 Requires only essential permissions (no creepy tracking, we promise).

---

## 🛠️ Project Structure

```bash
Volumancer/
├── app/
│   ├── src/main/
│   │   ├── java/com/dutch/volumancer/
│   │   │   ├── AppConstants.kt          # Shared constants
│   │   │   ├── MediaVolumeHelper.kt     # Helper for media volume control
│   │   │   ├── VolumancerApplication.kt # Application class
│   │   │   ├── VolumeWidgetProvider.kt  # Widget provider logic
│   │   │   ├── WidgetService.kt         # Background service for widget events
│   │   │   ├── MainService.kt           # Foreground service for floating UI
│   │   │   └── FloatingBall.kt          # Quick Ball UI logic
│   │   └── AndroidManifest.xml
│   └── res/
│       └── layout/, drawable/, values/, etc.
└── README.md ← you are here

