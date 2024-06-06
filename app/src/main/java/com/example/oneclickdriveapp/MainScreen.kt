package com.example.oneclickdriveapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.oneclickdriveapp.viewmodel.CalculationViewModel

@Composable
fun MainScreen(viewModel: CalculationViewModel) {

    val text1 = viewModel.text1
    val text2 = viewModel.text2
    val text3 = viewModel.text3


    val result by viewModel.result.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = text1,
            onValueChange = { viewModel.text1 = it },
            label = { Text("TextBox1") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = text2,
            onValueChange = { viewModel.text2 = it },
            label = { Text("TextBox2") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = text3,
            onValueChange = { viewModel.text3 = it },
            label = { Text("TextBox3") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            keyboardController?.hide()
            viewModel.calculate(text1, text2, text3)
        }) {
            Text("Calculate")
        }
        Spacer(modifier = Modifier.height(16.dp))

        result?.let {
            if (it.isSuccess) {
                val data = it.getOrNull()
                data?.let { calculationResult ->
                    Text("Intersection: ${calculationResult.intersection}")
                    Text("Union: ${calculationResult.union}")
                    Text("Highest Number: ${calculationResult.highestNumber}")
                }
            } else if (it.isFailure) {
                val exception = it.exceptionOrNull()
                Text("Error: ${exception?.message}")
            } else {

            }
        } ?: run {
            Text("Enter numbers and press calculate.")
        }

    }
}