package me.timschneeberger

import java.io.IOException
import java.nio.file.Path

fun String.execute(workingDir: Path? = null): Process? {
    return try {
        ProcessBuilder(
            listOf("sh", "-c" , this)
        )
            .directory(workingDir?.toFile())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
            .also {
                it.waitFor()
                if (it.exitValue() != 0) {
                    println("Error: ${it.errorStream.bufferedReader().readText()}")
                    return null
                }
            }
    } catch(e: IOException) {
        e.printStackTrace()
        null
    }
}

fun String.executeAndRead(workingDir: Path? = null): String? {
    return try {
        execute(workingDir)?.let {
            it.inputStream
                .bufferedReader()
                .readText()
        }
    } catch(e: IOException) {
        e.printStackTrace()
        null
    }
}