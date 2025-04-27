# android-window-dumper

Dump detailed view hierarchies for non-debuggable apps using ADB & WindowManagerService.

The layout inspector in Android Studio does not support layout inspection of non-debuggable apps. 
As a workaround, this app collects a layout dump manually and creates *.li files for each window (including system windows/overlays!).
Alternative layout dump methods using `uiautomator` only record basic view information and are not viable for my debugging purposes.

*.li files can be viewed in Android Studio after installing [Legacy layout inspector](https://plugins.jetbrains.com/plugin/19451-legacy-layout-inspector) [(source code)](https://github.com/pingfangx/androidstudiox/tree/master/legacy-layout-inspector).
Then just drag-and-drop a file into the window.

### Usage

```bash
./gradlew shadowJar
java -jar ./build/libs/android-window-dumper-*-SNAPSHOT-all.jar
```
The app runs in Linux environments and requires `adb` and `unzip` to be installed and accessible in `$PATH`.
