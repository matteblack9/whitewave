package com.whitewave.whitewave.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.http.*

import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.hc.core5.http.io.entity.StringEntity


fun Map<*, *>.toJsonObject(): JsonObject {
        val map = mutableMapOf<String, JsonElement>()
        this.forEach {
            if (it.key is String) {
                map[it.key as String] = Json.parseToJsonElement(it.value as String)
            }
        }
        return JsonObject(map)
    }

//    suspend fun convertResponseToString(response: HttpResponse): String {
//        val jsonElement = Json.parseToJsonElement(response.bodyAsText())
//        return jsonElement.jsonObject["message"]?.jsonObject?.get("result")?.jsonObject?.get("translatedText").toString();
//    }

//suspend fun sendPromptToLLM(modelType: String, modelAPI: String, command: String) {
//    val bodyMap = HashMap<String, String>();
//    bodyMap.put("model", modelType);
//    bodyMap.put("prompt", command);
//
//    val jsonString = bodyMap.toJsonObject().
//
//    client.use {
//       val httpResponse = it.post(modelAPI) {
//        contentType(ContentType.Application.Json)
//        setBody(jsonString)
//       }
//
//        val stringBody: String = httpResponse.body()
//        println(stringBody)
//    }
//}

    fun httpPost(urlString: String, headersMap: Map<String, String>?, parameterMap: Map<String, String>?, requestBody: Map<String, Any>?): Map<String, Any> {
        val gson = Gson()
        val jsonString = gson.toJson(requestBody)

        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(urlString).apply {
            entity = StringEntity(jsonString, ContentType.APPLICATION_JSON)
            headersMap?.forEach { (key, value) ->
                addHeader(key, value)
            }
        }

        httpClient.execute(httpPost).use { response ->
            val entity = response.entity
            val responseString = EntityUtils.toString(entity, "UTF-8")

            return gson.fromJson(responseString, object : TypeToken<Map<String, Any>>() {}.type)
        }
    }

public fun gptAnswer(urlString: String, prompt: String, token: String): String {
    val gson = Gson()
    val jsonString = """{
        "model": "gpt-3.5-turbo",
        "messages": [{"role": "user", "content": "${prompt}"}],
        "temperature": 0.7
    }"""

    val httpClient = HttpClients.createDefault()
    val httpPost = HttpPost(urlString).apply {
        entity = StringEntity(jsonString, ContentType.APPLICATION_JSON)
        addHeader("Content-Type", "application/json")
        addHeader("Authorization", "Bearer ${token}")
    }

    httpClient.execute(httpPost).use { response ->

        val entity = response.entity
        val responseString = EntityUtils.toString(entity, "UTF-8")
        val response = gson.fromJson(responseString, ApiResponse::class.java)

        return response.choices.firstOrNull()?.message?.content ?: "No content found"
    }
}

data class ApiResponse(
        val id: String,
        val obj: String,
        val created: Long,
        val model: String,
        val choices: List<Choice>,
        val usage: Usage,
        val system_fingerprint: Any?
)

data class Choice(
        val index: Int,
        val message: Message,
        val finish_reason: String
)

data class Message(
        val role: String,
        val content: String
)

data class Usage(
        val prompt_tokens: Int,
        val completion_tokens: Int,
        val total_tokens: Int
)