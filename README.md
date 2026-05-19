# SmoothLagFix-1.16.5

A high-performance optimization and utility mod for Fabric 1.16.5 designed to maximize FPS, eliminate stuttering, and keep servers clean.

## 🚀 Key Optimizations

- **Fast Math**: Replaces standard trigonometric functions (sin, cos) with high-speed lookup tables. This significantly reduces CPU overhead for rendering, animations, and entity movement.
- **AI Throttling (Brain Ticks)**: Intelligently reduces the frequency of complex AI "Brain" updates for entities. This saves massive server-side CPU time without noticeably affecting entity behavior.
- **Entity Tick Culling Foundation**: Built-in framework for skipping unnecessary entity calculations when far from active players.
- **GC Reduction**: Optimized code paths to minimize temporary object allocations, leading to fewer garbage collection spikes and smoother frame times.

## 🧹 Lag Management Features

- **Automatic Lag Clear**: Automatically clears all items dropped on the ground every **5 minutes** to prevent entity-based lag.
- **Manual Command**: Administrators can manually trigger a cleanup using the **`/lagclear`** command (requires OP/Permission Level 2).
- **Cleanup Notifications**: Displays a clean, colored message in chat whenever items are cleared.

## 🛠 Features

- **Extreme Smoothness**: Specifically tuned to improve 1% low FPS, making the game feel much more fluid.
- **Low Profile**: Designed to be lightweight and compatible with other major optimization mods like Lithium or Sodium.
- **Universal**: Works perfectly on both single-player clients and dedicated servers.

## 📦 Installation

1. Ensure you have **Fabric Loader** installed for Minecraft 1.16.5.
2. Download the latest `smoothlagfix-1.0.0.jar`.
3. Place the JAR into your `.minecraft/mods` folder.
4. (Optional) Run `/lagclear` in-game to test the installation.

## ⚠️ Building from Source

This project uses Gradle. To build the mod yourself:
```bash
./gradlew build
```
The compiled JAR will be located in `build/libs/`.
