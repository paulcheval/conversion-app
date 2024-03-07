package com.example.conversionapp

data class ConversionUiState(
    val food: String = "",
    val fromUnit: String = "",
    val fromAmount: Double = 1.0,
    val targetUnit: String="",
    val targetAmount: Double = 0.0,
    val availableFoods: List<String> = emptyList(),
    val uoms: List<String>? = emptyList(),
    val foodUoms: Map<String, List<String>> = emptyMap(),
    val selectedFood: String = "",
    val selectedUom: String = "",
    val selectedUomIndex: Int = 0,
    val selectedFoodIndex: Int = 0,
) {

}
