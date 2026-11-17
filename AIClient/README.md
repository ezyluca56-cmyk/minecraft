# AI Client (Minecraft 26.2, Fabric)

A utility/cheat client packaged as a Fabric mod: ClickGUI, HUD, per-module keybinds and 27 modules.

## Requirements
- JDK 25
- Gradle 9.5+ (only needed once, to create the wrapper)
- Minecraft 26.2 with Fabric Loader 0.19.3+ and Fabric API 0.160.0+26.2

## Build
```
gradle wrapper --gradle-version 9.5.1     # one time, creates ./gradlew
./gradlew build
```
The jar ends up in `build/libs/aiclient-1.0.0.jar`. Drop it in your `mods` folder next to Fabric API.
To test inside your IDE instead: `./gradlew runClient`.

If a version fails to resolve, check https://fabricmc.net/develop and edit `gradle.properties`.

## Using it
- **Right Shift** opens the ClickGUI
  - left click a module = toggle, right click = show its settings
  - boolean setting: left click toggles; number setting: left click = +, right click = -
- Every module also has a keybind in **Options > Controls > Key Binds > AI Client** (most are unbound; set your own).
  Defaults: R = KillAura, G = Flight, B = Fullbright, X = XRay, C = Zoom.

## Modules
| Category | Modules |
|---|---|
| Combat | KillAura, Triggerbot, AimAssist, AutoClicker, Reach, Velocity, AutoTotem |
| Movement | Flight, Speed, Sprint, NoFall, Step, HighJump, Jesus, Spider, Glide, AirJump, AutoWalk |
| Player | AutoRespawn, AntiAFK, AutoEat |
| Render | Fullbright, ESP, XRay, Zoom |
| World | Scaffold, Nuker |

## Project layout
```
src/main/java/dev/aiclient/
  AIClient.java          entrypoint (keybinds, tick loop, HUD registration)
  hack/Hack.java         base class every module extends
  hack/HackManager.java  module list + keybind handling
  hack/<category>/       the modules
  gui/                   ClickGuiScreen + Hud
  setting/               BoolSetting, NumSetting
  util/                  TargetUtil, Attr
  mixin/BlockMixin.java  XRay hook
```
To add a module: create a class extending `Hack`, then add it in `HackManager.init()`, and add a line for its keybind name in `assets/aiclient/lang/en_us.json`.

## Notes
- Written against the unobfuscated 26.x names (Mojang names, `Identifier`, `GuiGraphicsExtractor`). It has not been compiled
  yet, so expect to fix a few small API-name mismatches on the first build.
- XRay uses a mixin with `require = 0`, so if the target method changes the game still starts and XRay just does nothing.
- Cheat modules are for singleplayer, your own server, or servers that allow them. Most public servers ban this and
  anti-cheat will flag Flight, Speed, KillAura, Reach, Scaffold, Nuker and similar modules.
