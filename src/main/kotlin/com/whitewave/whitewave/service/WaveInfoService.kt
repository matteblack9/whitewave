package com.whitewave.whitewave.service

import com.whitewave.whitewave.repository.SpotStatusRepository
import com.whitewave.whitewave.repository.SurfSpotRepository
import com.whitewave.whitewave.utils.httpPost
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap


@Service
class WaveInfoService (private val spotStatusRepository: SpotStatusRepository,
                       private val surfSpotRepository: SurfSpotRepository) {
    @Value("\${windy.point.api.key}")
    lateinit var windyPointApiKey: String

    @Value("\${windy.point.api.url}")
    lateinit var windyPointUrl: String

    fun combineWeatherStatus(latitude: Double, longitude: Double) {
        val surfSpots =  surfSpotRepository.findAll();
        surfSpots.forEach { surfspot ->

        }
    }

    public fun getWeatherStatus(latitude: Double, longitude: Double, forecastModel: String): Map<String, Any> {
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