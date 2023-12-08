package com.whitewave.whitewave


import FORECAST_MODEL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.whitewave.whitewave.model.SpotStatus
import com.whitewave.whitewave.model.SpotStatusId
import com.whitewave.whitewave.repository.SpotStatusRepository
import com.whitewave.whitewave.repository.SurfSpotRepository
import com.whitewave.whitewave.service.WaveInfoService
import com.whitewave.whitewave.utils.CommonUtil
import com.whitewave.whitewave.utils.convertPosixTimeToCurrentTime
import kotlinx.serialization.descriptors.StructureKind
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.util.*


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@SpringBootTest
@AutoConfigureMockMvc
class WhiteWaveApplicationTests @Autowired constructor (
        private val surfSpotRepository: SurfSpotRepository,
        private val spotStatusRepository: SpotStatusRepository,
        private val waveInfoService: WaveInfoService) {

    @Test
    fun `Assert blog page title, content and status code`() {
       val surfSpot = surfSpotRepository.findBySpotName("금진해변")
        println(surfSpot.spotName + surfSpot.latitude + surfSpot.longitude)
    }


    @Test
    fun `Convert Json String to Map `() {
        val gson = Gson()

        val surfSpot = surfSpotRepository.findBySpotName("금진해변")
//        val result = waveInfoService.getWeatherStatus(surfSpot.latitude, surfSpot.longitude, FORECAST_MODEL.GFS_WAVE);

//        val newSpotStatusId = SpotStatusId(spotName = "금진해변", timeStamp = "")
//        val newSpotStatus = SpotStatus()

        val filePath = "./response_sample.json"
        val bufferedReader = File(filePath).bufferedReader(Charsets.UTF_8)
        val content = bufferedReader.use { it.readText() }

//        ts
//        units
//        waves_height-surface
//        waves_direction-surface
//        waves_period-surface
//        wwaves_height-surface
//        wwaves_direction-surface
//        wwaves_period-surface
//        swell1_height-surface
//        swell1_direction-surface
//        swell1_period-surface
//        swell2_height-surface
//        swell2_direction-surface
//        swell2_period-surface

//        println(content)
        val resultMap : Map<String, Any> = gson.fromJson(content, object : TypeToken<Map<String, Any>>() {}.type)


        println("result = " + convertPosixTimeToCurrentTime(1702198800000.toString()))
    }
}