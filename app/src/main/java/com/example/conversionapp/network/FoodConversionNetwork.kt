package com.example.conversionapp.network

import android.util.Log
import com.example.conversionapp.ConversionViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class FoodConversionNetwork : FoodConversionClientService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("application", "json"))
        }
    }

    public override suspend fun retrieveAvailableFoodsFromNetwork(): List<String> {
        val response = client.get(NetworkConstants.FOODS_SERVICE_URL) {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    public override suspend fun retrieveUomsForGivenFoodFromNetwork(food: String): List<String> {
        val path = "${food}" + "/uom"
        val response = client.get("http://10.0.0.187:8080/foods") {
            url {
                appendPathSegments("Flour", "uom")
            }
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    public override suspend fun retrieveAllUomsForAllFoodFromNetwork(): Map<String, List<String>> {
        val response = client.get("http://10.0.0.187:8080/foods/uom") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    public override suspend  fun retrieveListOfTriviaQuestionsFromNetwork(request: ConversionRequestNetwork): ConversionResponseNetwork {
        val theRequest = ConversionRequestNetwork(
            food = request.food,
            fromUnit = request.fromUnit,
            targetUnit = request.targetUnit,
            fromAmount = request.fromAmount)

        Log.d("post call", "sending request - $theRequest")



        val response =  client.post(NetworkConstants.CONVERSION_SERVICE_URL)  {
            contentType(ContentType.Application.Json)
            setBody(theRequest)
        }

        Log.d("post call", "response $response.status")
        Log.d("post call", "response $response")

        Log.d("post call", "response ${response.body<ConversionResponseNetwork>()}")
        return response.body<ConversionResponseNetwork>()
    }
}
