package me.timschneeberger

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ObjectOutputStream
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.io.path.*

@OptIn(ExperimentalPathApi::class)
fun main() {
    if("which adb".execute()?.exitValue() != 0) {
        println("adb not found in PATH")
        return
    }

    val workDir = createTempDirectory()
    val screenFile = "screen.png"

    "adb shell wm dump-visible-window-views >${workDir}/windows.zip".execute(workDir)
    "adb exec-out screencap -p >$screenFile".execute(workDir)
    "unzip -o -qq $workDir/windows.zip -x -d $workDir".execute(workDir)

    val screenBytes = workDir.resolve(screenFile).readBytes()

    workDir.resolve("windows.zip").deleteIfExists()
    workDir.resolve(screenFile).deleteIfExists()

    workDir.toFile().walkTopDown().forEach { file ->
        if (file.isDirectory)
            return@forEach

        val hierarchy = file.readBytes()

        val bytes = ByteArrayOutputStream(4096)
        with(ObjectOutputStream(bytes)) {
            writeUTF("""{"version":"2","title":"${file.name}"}""".toString())
            writeInt(hierarchy.size)
            write(hierarchy)
            writeInt(screenBytes.size)
            write(screenBytes)
            close()
        }

        val timestamp = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())
        File("$timestamp.${file.name}.li").writeBytes(bytes.toByteArray())
    }

    workDir.deleteRecursively()
}