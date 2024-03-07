package com.example.conversionapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.conversionapp.network.ConversionRequestNetwork
import com.example.conversionapp.network.FoodConversionClientService
import com.example.conversionapp.network.FoodConversionMockService
import com.example.conversionapp.network.FoodConversionNetwork
import com.example.conversionapp.network.NetworkConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConversionViewModel(application: Application): AndroidViewModel(application) {
    private val _uiState = MutableStateFlow((ConversionUiState()))
    val uiState: StateFlow<ConversionUiState> = _uiState.asStateFlow()
    private val context = application


    init {
        loadData()
    }
    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val availableFoodUomMap = determineFoodConversionService().retrieveAllUomsForAllFoodFromNetwork()
            Log.d("ConversionViewModel-loadData", "Retrieved food-uoms $availableFoodUomMap")

            _uiState.update { currentState ->
                currentState.copy(
                   foodUoms = availableFoodUomMap
                )
            }
        }
    }

    fun getAvailableFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val foods = determineFoodConversionService().retrieveAvailableFoodsFromNetwork()

            _uiState.update { currentState ->
                currentState.copy(
                    availableFoods = foods
                )
            }

        }
    }

    fun getAllUomForAllAvailableFoods() {
        viewModelScope.launch(Dispatchers.IO) {
            val foodUomMap = determineFoodConversionService().retrieveAllUomsForAllFoodFromNetwork();

            _uiState.update { currentState ->
                currentState.copy(
                    foodUoms = foodUomMap
                )
            }
        }
    }
    fun getUomForGivenFood() {
        viewModelScope.launch(Dispatchers.IO) {
            val uoms = determineFoodConversionService().retrieveUomsForGivenFoodFromNetwork(uiState.value.food)

            _uiState.update { currentState ->
                currentState.copy(
                    uoms = uoms
                )
            }

        }
    }


    fun getConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            val conversion = determineFoodConversionService().retrieveListOfTriviaQuestionsFromNetwork(
                ConversionRequestNetwork(
                    food = "Flour",
                    fromUnit = "cup",
                    fromAmount = 2.0,
                    targetUnit = "g"
                )
            )
            _uiState.update { currentState ->
                currentState.copy(
                    fromAmount = conversion.fromAmount,
                    fromUnit = conversion.fromUnit,
                    targetUnit = conversion.targetUnit,
                    targetAmount = conversion.targetAmount
                )
            }
        }
    }

    fun updateSelectedItem(item: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedFood = item
            )
        }
    }

    fun updateSelectedUom(item: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedUom = item
            )
        }
    }

    fun updateSelectedUomIndex(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedUomIndex = index
            )
        }
    }

    fun updateSelectedFoodIndex(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedFoodIndex = index
            )
        }
    }

    fun updateFromAmount(amount: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                fromAmount = amount
            )
        }
    }

    private fun determineFoodConversionService() : FoodConversionClientService{
        if (NetworkConstants.USE_MOCK) {
            return FoodConversionMockService()
        }
        return FoodConversionNetwork()
    }

    fun determineSelectedUomName(): String {
        val uoms = _uiState.value.foodUoms.get(_uiState.value.selectedFood) ?: return ""
        return uoms.get(_uiState.value.selectedUomIndex) ?: return ""
    }



}