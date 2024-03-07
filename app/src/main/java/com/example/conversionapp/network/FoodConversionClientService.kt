package com.example.conversionapp.network

interface FoodConversionClientService  {
    public suspend fun retrieveAvailableFoodsFromNetwork(): List<String>
    public suspend fun retrieveUomsForGivenFoodFromNetwork(food: String): List<String>?
    public suspend fun retrieveAllUomsForAllFoodFromNetwork(): Map<String, List<String>>
    public suspend  fun retrieveListOfTriviaQuestionsFromNetwork(request: ConversionRequestNetwork): ConversionResponseNetwork

}