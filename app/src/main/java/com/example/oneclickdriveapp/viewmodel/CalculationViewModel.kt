package com.example.oneclickdriveapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneclickdriveapp.CalculationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalculationViewModel : ViewModel() {
    var text1 by mutableStateOf("")
    var text2 by mutableStateOf("")
    var text3 by mutableStateOf("")

    private val _result = MutableStateFlow<Result<CalculationResult>?>(null)
    val result: StateFlow<Result<CalculationResult>?> = _result



    fun calculate(text1: String, text2: String, text3: String) {
        viewModelScope.launch {
            try {
                val list1 = text1.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
                val list2 = text2.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()
                val list3 = text3.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()

                val intersection = list1.intersect(list2).intersect(list3)
                val union = list1.union(list2).union(list3)
                val maxNumber = union.maxOrNull() ?: throw Exception("No valid numbers found")

                _result.value = Result.success(
                    CalculationResult(
                        intersection = intersection.joinToString(", "),
                        union = union.joinToString(", "),
                        highestNumber = maxNumber.toString()
                    )
                )
            } catch (e: Exception) {
                _result.value = Result.failure(e)
            }
        }
    }


}