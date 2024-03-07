package com.example.conversionapp.network

import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

class FoodConversionMockService: FoodConversionClientService {

    private val foodConversionMap: Map<String, List<String>> = mapOf("Flour" to listOf<String>("cups", "g", "oz"),
                                                            "Sugar" to listOf<String>("cups", "g", "oz"),
                                                            "Tomato Paste" to listOf<String>("tsp", "g"))
    override suspend fun retrieveAvailableFoodsFromNetwork(): List<String> {
        return listOf("Flour", "Sugar", "Tomato Paste")
    }

    override suspend fun retrieveUomsForGivenFoodFromNetwork(food: String): List<String>? {
        return foodConversionMap.get(food)

    }

    override suspend fun retrieveAllUomsForAllFoodFromNetwork(): Map<String, List<String>> {
        return foodConversionMap
    }

    override suspend fun retrieveListOfTriviaQuestionsFromNetwork(request: ConversionRequestNetwork): ConversionResponseNetwork {
        return ConversionResponseNetwork(
            fromUnit = request.fromUnit,
            fromAmount = request.fromAmount,
            food = request.food,
            targetUnit = request.targetUnit,
            targetAmount = request.fromAmount *3
        )
    }
}