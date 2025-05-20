# Volumancer
# 🎚️ Volumancer

Volumancer is a minimalist Android utility app (targeting Android 11 and above) that lets you control **media volume** seamlessly using a **home screen widget** and a floating **quick-ball-style UI**.

No bloated UI. No unnecessary permissions. Just elegant control at your fingertips. 🧙‍♂️

---

# Why I created this
You know those phones where the volume buttons just stop working? It’s frustrating, and it happens more often than it should. On top of that, Android’s default quick ball or floating shortcuts barely offer decent volume controls. It’s like that important feature was completely overlooked.
As an Android developer, I figured: why wait for someone else to fix this? So, I built Volumancer — a simple, focused app that does one thing really well: lets you control your media volume quickly and easily, no fuss.
At the end of the day, what’s the point of being a developer if you can’t solve your own problems? This app is my way of making Android work the way it should — for me, and for anyone else tired of volume button headaches.



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

