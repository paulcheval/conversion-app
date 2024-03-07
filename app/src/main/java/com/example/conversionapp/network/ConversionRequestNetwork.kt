package com.example.conversionapp.network

import kotlinx.serialization.Serializable

@Serializable
data class ConversionRequestNetwork(
    val food: String,
    val fromUnit: String,
    val fromAmount: Double,
    val targetUnit: String,
)
