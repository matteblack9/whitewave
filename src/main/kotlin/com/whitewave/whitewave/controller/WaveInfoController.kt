package com.whitewave.whitewave.controller;


import com.whitewave.whitewave.repository.SurfSpotRepository
import com.whitewave.whitewave.service.WaveInfoService
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class WaveInfoController(private val waveInfoService: WaveInfoService, private val surfSpotRepository: SurfSpotRepository){
    @ApiOperation(value = "deleteSecret", notes = "성공 시, 해당하는 namespace 내에 있는 secret을 삭제합니다")
    @ApiImplicitParams(
            ApiImplicitParam(name = "spotName", value = "서핑할 바다 이름", required = true, dataType = "String"),
            ApiImplicitParam(name = "model", value = "불러올 날씨 모델", required = true, dataType = "String"))
    @PostMapping("/wave/info/")
    fun getWaveInformation(spotName: String, @RequestParam model: String) {
        val surfSpot = surfSpotRepository.findBySpotName(spotName)

//      println(waveInfoService.getWeatherStatus(37.6364817, 129.0449615, model));
    }

    @GetMapping("/wave-status")
    fun getWaveStatus(@RequestParam(name = "spotName") spotName: String) : String {
        return waveInfoService.getWeatherMetric(spotName)
    }
}
