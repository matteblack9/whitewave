package com.whitewave.whitewave

import com.whitewave.whitewave.service.WaveInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Service
import springfox.documentation.swagger2.annotations.EnableSwagger2



//@EnableJpaRepositories("com.whitewave.whitewave.repository")
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


    suspend fun main(args: Array<String>) {
//    val videoUrl = "https://wsb.live.smilecdn.com/484600858/main/chunklist_w998108730_vo_sfm4s.m3u8"
//    val outputPath = "frame_capture.jpg"
//    val frameTime = "00:00:10" // Capture frame at 10 seconds
//
//    captureFrame(videoUrl, outputPath, frameTime)

//    val host = "http://localhost:11434/"
//    val ollamaAPI = OllamaAPI(host)
//
//    while (true) {
//        try {
//            print('\t')
//            var command: String? = readLine()
//            val translatedCommand = command?.let { translate(it, "https://openapi.naver.com/v1/papago/n2mt", "20H_bfBYcToz2XtrR4wd", "gChFtNcUr3", "ko", "en") }.orEmpty()
//            val result: OllamaResult = ollamaAPI.ask(OllamaModelType.LLAMA2, translatedCommand)
//            val translatedResult = result.response?.let { translate(it, "https://openapi.naver.com/v1/papago/n2mt", "20H_bfBYcToz2XtrR4wd", "gChFtNcUr3", "en", "ko") }.orEmpty()
//            println(translatedResult)
//        }
//        catch (exception: Exception) {
//            println(exception.message)
//        }
//
//    }

//        println(waveInfoService.getWeatherStatus(37.6364817, 129.0449615, "gfs"))

        runApplication<WhiteWaveApplication>(*args)
    }