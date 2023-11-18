package com.whitewave.whitewave

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhiteWaveApplication

fun captureFrame(videoPath: String, outputPath: String, frameTime: String) {
    try {
        val processBuilder = ProcessBuilder(
            "ffmpeg",
            "-i", videoPath,
            "-ss", frameTime,
            "-vframes", "1",
            outputPath
        )

        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        val inputStream = process.inputStream
        inputStream.bufferedReader().use {
            it.lines().forEach { line -> println(line) }
        }

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw RuntimeException("ffmpeg exited with code $exitCode")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun main(args: Array<String>) {
    val videoUrl = "https://wsb.live.smilecdn.com/484600858/main/chunklist_w998108730_vo_sfm4s.m3u8"
    val outputPath = "frame_capture.jpg"
    val frameTime = "00:00:10" // Capture frame at 10 seconds

    captureFrame(videoUrl, outputPath, frameTime)

    runApplication<WhiteWaveApplication>(*args)
}
