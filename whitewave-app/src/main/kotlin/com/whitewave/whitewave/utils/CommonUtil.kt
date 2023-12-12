package com.whitewave.whitewave.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class CommonUtil {}

//fun convertDateToString(date: Date) : String {
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    val formattedDate = dateFormat.format(date)
//    return formattedDate
//}
//fun convertPosixTimeToCurrentTime(posixTime: String): Date {
//    return convertPosixTimeToCurrentTime(posixTime.toLong())
//}
//
//fun convertPosixTimeToCurrentTime(posixTime: Long): String {
//    val date = Date(posixTime) // Date 객체 생성
//    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    val formattedDate = dateFormat.format(date)
//    return formattedDate
//}