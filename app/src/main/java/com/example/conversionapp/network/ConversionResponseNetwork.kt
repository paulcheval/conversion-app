package com.example.conversionapp.network

import kotlinx.serialization.Serializable

@Serializable
data class ConversionResponseNetwork(
    val food: String,
    val fromUnit: String,
    val fromAmount: Double,
    val targetUnit: String,
    val targetAmount: Double,
)
