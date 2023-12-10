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
import com.whitewave.whitewave.utils.gptAnswer
//import com.whitewave.whitewave.utils.convertPosixTimeToCurrentTime
import com.whitewave.whitewave.variable.FORECAST_PARAMETER
import kotlinx.serialization.descriptors.StructureKind
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

//    @Test
//    fun `Assert blog page title, content and status code`() {
//       val surfSpot = surfSpotRepository.findBySpotName("금진해변")
//        println(surfSpot.spotName + surfSpot.latitude + surfSpot.longitude)
//    }

    @Value("\${openai.token}")
    lateinit var openAIToken: String

    @Test
    fun `Convert Json String to Map `() {
        val spotName = "금진해변"
        var videoUrl = ""
        if (spotName.equals("금진해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/484600858/main/chunklist_w998108730_vo_sfm4s.m3u8"
        } else if (spotName.equals("죽도해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/wsbrtsp/stream.stream/chunklist_w1521043097.m3u8"
        } else if (spotName.equals("설악해변")) {
            videoUrl = "https://wsb.live.smilecdn.com/wsbrtsp18/stream18.stream/chunklist_w625106692.m3u8"
        } else {
            ;
        }

        val outputPath = "/Users/user/kotlinProject/whitewave-frontend/" + "${spotName}.jpg"
        val frameTime = "00:00:10" // Capture frame at 10 seconds

//        captureFrame(videoUrl, outputPath, frameTime)
    }

//    curl https://api.openai.com/v1/chat/completions \
//    -H "Content-Type: application/json" \
//    -H "Authorization: Bearer ${MY_TOKEN}" \
//    -d '{
//        "model": "gpt-4-0314",
//        "messages": [{"role": "user", "content": "hello world"}],
//        "temperature": 0.7
//    }'

//    curl https://api.openai.com/v1/models \
//    -H "Authorization: Bearer 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" \
//    -H "OpenAI-Organization: org-L5nxXGjx0cYti5dEHNO763Oa"
}