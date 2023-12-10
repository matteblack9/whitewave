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
//import com.whitewave.whitewave.utils.convertPosixTimeToCurrentTime
import com.whitewave.whitewave.variable.FORECAST_PARAMETER
import kotlinx.serialization.descriptors.StructureKind
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timer


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

        val filePath = "./response_sample.json"
        val bufferedReader = File(filePath).bufferedReader(Charsets.UTF_8)
        val content = bufferedReader.use { it.readText() }

        val resultMap : Map<String, Any> = gson.fromJson(content, object : TypeToken<Map<String, Any>>() {}.type)
        val posixTimes : List<Long> = resultMap[FORECAST_PARAMETER.TIME_STAMP] as List<Long>
        posixTimes.forEachIndexed { index, posixTimestamp ->

                val kstTimestamp = Date(posixTimestamp)
                val spotStatusId = SpotStatusId(spotName = "금진해변", timeStamp = kstTimestamp)

                val wavesHeight: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_HEIGHT) as List<Double>).getOrNull(index)
                val wavesDirection: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_DIRECTION) as List<Double>).getOrNull(index)
                val wavesPeriod: Double? = (resultMap.get(FORECAST_PARAMETER.WAVES_PERIOD) as List<Double>).getOrNull(index)
                val wwavesHeight: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_HEGIHT) as List<Double>).getOrNull(index)
                val wwavesDirection: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_DIRECTION) as List<Double>).getOrNull(index)
                val wwavesPeriod: Double? = (resultMap.get(FORECAST_PARAMETER.WWAVES_PERIOD) as List<Double>).getOrNull(index)
                val swell1Height: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1_HEIGHT) as List<Double>).getOrNull(index)
                val swellDiection: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1_DIRECTION) as List<Double>).getOrNull(index)
                val swell1Period: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL1__PERIOD) as List<Double>).getOrNull(index)
                val swell2Height: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_HEIGHT) as List<Double>).getOrNull(index)
                val swell2Direciton: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_DIRECTION) as List<Double>).getOrNull(index)
                val swell2Period: Double? = (resultMap.get(FORECAST_PARAMETER.SWELL2_PERIOD) as List<Double>).getOrNull(index)

                val prompt = "어떤 바다에서 서핑을 한다고 했을 때," +
                        (wwavesHeight?:"파도의 높이가 ${wwavesHeight}${FORECAST_PARAMETER.WWAVES_HEGIHT_UNIT} ") +
                        (wwavesDirection?:"파도의 방향이 ${wwavesDirection}${FORECAST_PARAMETER.WWAVES_DIRECTION_UNIT} ") +
                        (wwavesPeriod?:"파도의 주기가 ${wwavesPeriod}${FORECAST_PARAMETER.WWAVES_PERIOD}") + " 그리고 " +
                        (swell1Height?:"스웰의 높이가 ${swell1Height}${FORECAST_PARAMETER.SWELL1_HEIGHT_UNIT} ") +
                        (swellDiection?:"스웰의 방향이 ${swellDiection}${FORECAST_PARAMETER.SWELL1_DIRECTION_UNIT} ") +
                        (swell1Period?:"스웰의 주기가 ${swell1Period}${FORECAST_PARAMETER.SWELL1_PERIOD_UNIT} ") +
                            "라고 한다면, 파도타기에 어떠한지 상세한 분석을 해줘. 그리고 파도 타기 좋은 점수를 5.0점 만점이라고 했을 때, 점수도 매겨줘"


            }
        }
    }
}