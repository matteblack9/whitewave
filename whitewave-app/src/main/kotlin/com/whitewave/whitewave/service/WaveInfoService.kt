package com.whitewave.whitewave.service

import FORECAST_MODEL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whitewave.whitewave.model.SpotStatusId
import com.whitewave.whitewave.repository.SpotStatusRepository
import com.whitewave.whitewave.repository.SurfSpotRepository
import com.whitewave.whitewave.utils.gptAnswer
import com.whitewave.whitewave.utils.httpPost
import com.whitewave.whitewave.variable.FORECAST_PARAMETER
import io.ktor.http.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import kotlin.collections.HashMap


@Service
class WaveInfoService (private val spotStatusRepository: SpotStatusRepository,
                       private val surfSpotRepository: SurfSpotRepository) {
    @Value("\${windy.point.api.key}")
    lateinit var windyPointApiKey: String

    @Value("\${windy.point.api.url}")
    lateinit var windyPointUrl: String

    @Value("\${openai.token}")
    lateinit var openAIToken: String

    private fun captureFrame(videoPath: String, outputPath: String, frameTime: String) {
        try {
            val processBuilder = ProcessBuilder(
                    "/opt/homebrew/bin/ffmpeg",
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

    fun getWeatherMetric(spotName: String) : String {
        val gson = Gson()
        val surfSpot = surfSpotRepository.findBySpotName(spotName)
        val resultMap = getWeatherStatusByWindy(surfSpot.latitude, surfSpot.longitude, FORECAST_MODEL.GFS_WAVE)

//        val filePath = "./response_sample.json"
//        val bufferedReader = File(filePath).bufferedReader(Charsets.UTF_8)
//        val content = bufferedReader.use { it.readText() }
//
//        val resultMap : Map<String, Any> = gson.fromJson(content, object : TypeToken<Map<String, Any>>() {}.type)
        val posixTimes : List<Long> = resultMap[FORECAST_PARAMETER.TIME_STAMP] as List<Long>
        val kstTimestamp = Date(posixTimes.get(0))

        val wavesHeight: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_HEIGHT) as List<Double>).getOrNull(0)
        val wavesDirection: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_DIRECTION) as List<Double>).getOrNull(0)
        val wavesPeriod: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_PERIOD) as List<Double>).getOrNull(0)
        val wwavesHeight: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_HEGIHT) as List<Double>).getOrNull(0)
        val wwavesDirection: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_DIRECTION) as List<Double>).getOrNull(0)
        val wwavesPeriod: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_PERIOD) as List<Double>).getOrNull(0)
        val swell1Height: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1_HEIGHT) as List<Double>).getOrNull(0)
        val swellDiection: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1_DIRECTION) as List<Double>).getOrNull(0)
        val swell1Period: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1__PERIOD) as List<Double>).getOrNull(0)
        val swell2Height: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_HEIGHT) as List<Double>).getOrNull(0)
        val swell2Direciton: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_DIRECTION) as List<Double>).getOrNull(0)
        val swell2Period: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_PERIOD) as List<Double>).getOrNull(0)

        val prompt = "어떤 바다에서 서핑을 한다고 했을 때," +
                (wwavesHeight?.let{"파도의 높이가 ${wwavesHeight}${FORECAST_PARAMETER.WWAVES_HEGIHT_UNIT} "} ?: "") +
                (wwavesDirection?.let{"파도의 방향이 ${wwavesDirection}${FORECAST_PARAMETER.WWAVES_DIRECTION_UNIT} "} ?: "") +
                (wwavesPeriod?.let{"파도의 주기가 ${wwavesPeriod}${FORECAST_PARAMETER.WWAVES_PERIOD_UNIT}"} ?: "") + " 그리고 " +
                (swell1Height?.let{"스웰의 높이가 ${swell1Height}${FORECAST_PARAMETER.SWELL1_HEIGHT_UNIT} "} ?: "") +
                (swellDiection?.let{"스웰의 방향이 ${swellDiection}${FORECAST_PARAMETER.SWELL1_DIRECTION_UNIT} "}?: "") +
                (swell1Period?.let{"스웰의 주기가 ${swell1Period}${FORECAST_PARAMETER.SWELL1_PERIOD_UNIT} "}?: "") +
                "라고 한다면, 파도타기에 어떠한지 상세한 분석을 해줘. 그리고 파도 타기 좋은 점수를 5.0점 만점이라고 했을 때, 점수도 매겨줘"

        val retStr = gptAnswer("https://api.openai.com/v1/chat/completions", prompt, openAIToken)

        var videoUrl: String = ""
        if (spotName.equals("금진해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/484600858/main/chunklist_w998108730_vo_sfm4s.m3u8"
        } else if (spotName.equals("죽도해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/wsbrtsp18/stream18.stream/chunklist_w625106692.m3u8"
        } else if (spotName.equals("설악해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/wsbrtsp18/stream18.stream/chunklist_w625106692.m3u8"
        } else {
            ;
        }

        val outputPath = "/Users/user/kotlinProject/whitewave-frontend/" + "${spotName}.jpg"
        val frameTime = "00:00:10" // Capture frame at 10 seconds

        captureFrame(videoUrl, outputPath, frameTime)

        return retStr
    }

    fun combineWeatherStatus(latitude: Double, longitude: Double) {
        val surfSpots =  surfSpotRepository.findAll();
        surfSpots.forEach { surfspot ->

        }
    }

    public fun getWeatherStatusByWindy(latitude: Double, longitude: Double, forecastModel: String): Map<String, Any> {
        val requestBody = HashMap<String, Any>()
        val weatherParameters = LinkedList<String>()
        val levels = LinkedList<String>()

        if (forecastModel.equals("gfs")) {
            weatherParameters.add("temp")
            weatherParameters.add("wind")
            weatherParameters.add("rh")
            weatherParameters.add("gh")
            weatherParameters.add("pressure")
            levels.add("surface")
        } else if (forecastModel.equals("gfsWave")) {
            weatherParameters.add("waves")
            weatherParameters.add("windWaves")
            weatherParameters.add("swell1")
            weatherParameters.add("swell2")
            weatherParameters.add("so2sm")
            weatherParameters.add("dustsm")
            levels.add("surface")
        } else if (forecastModel.equals("geos5")){
            weatherParameters.add("so2sm")
            weatherParameters.add("dustsm")
        } else {

        }

        requestBody.put("lat", latitude)
        requestBody.put("lon", longitude)
        requestBody.put("model", forecastModel)
        requestBody.put("parameters", weatherParameters)
        requestBody.put("levels", levels)
        requestBody.put("key", windyPointApiKey)


        val resultMap = httpPost(windyPointUrl, null, null, requestBody)
        return resultMap
    }
}