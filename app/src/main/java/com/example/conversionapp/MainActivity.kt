package com.example.conversionapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.conversionapp.ui.theme.ConversionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ConversionApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionApp() {
    val viewModel: ConversionViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var foodListExpanded by remember {
        mutableStateOf(false)
    }

    var fromUomListExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {


        if (uiState.foodUoms.isNotEmpty()) {

            Box(modifier = Modifier.width(150.dp)) {
                OutlinedTextField(
                    label = { Text(text = "Food") },
                    value = uiState.foodUoms.keys.elementAt(uiState.selectedFoodIndex),
                    enabled = false,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    placeholder = { Text(text = "food") },
                    trailingIcon = {
                        if (foodListExpanded) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = null
                            )
                        } else {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        //For Icons
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier
                        .clickable(onClick = { foodListExpanded = true })
                        .fillMaxWidth()
                )


                DropdownMenu(
                    expanded = foodListExpanded,
                    onDismissRequest = { foodListExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.foodUoms.keys.forEachIndexed { index, food ->
                        DropdownMenuItem(
                            text = { Text(text = food) },
                            onClick = {
                                foodListExpanded = false
                                viewModel.updateSelectedItem(food)
                                viewModel.updateSelectedFoodIndex(index)
                            }
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(thickness = 2.dp)

            Box(modifier = Modifier.width(150.dp)) {
                OutlinedTextField(
                    label = { Text(text = "Starting Unit of Measure") },
                    value = viewModel.determineSelectedUomName(),
                    enabled = false,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    placeholder = { Text(text = "food") },
                    trailingIcon = {
                        if (fromUomListExpanded) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = null
                            )
                        } else {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        //For Icons
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier
                        .clickable(onClick = { fromUomListExpanded = true })
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = fromUomListExpanded,
                    onDismissRequest = { fromUomListExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.foodUoms.get(uiState.selectedFood)?.forEachIndexed { index, uom ->
                        DropdownMenuItem(
                            text = { Text(text = uom) },
                            onClick = {
                                fromUomListExpanded = false
                                viewModel.updateSelectedUomIndex(index)
                            })

                    }
                }
            }
            OutlinedTextField(
                label = { Text(text = "Quantity") },
                value = uiState.fromAmount.toString(),
                enabled = true,
                onValueChange = { viewModel.updateFromAmount(it.toDouble())},
                readOnly = false,
                singleLine = true,
                placeholder = { Text(text = "food") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Box(modifier = Modifier.width(150.dp)) {
                OutlinedTextField(
                    label = { Text(text = "Target Unit of Measure") },
                    value = viewModel.determineSelectedUomName(),
                    enabled = false,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    placeholder = { Text(text = "food") },
                    trailingIcon = {
                        if (fromUomListExpanded) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = null
                            )
                        } else {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = null
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        //For Icons
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    modifier = Modifier
                        .clickable(onClick = { fromUomListExpanded = true })
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = fromUomListExpanded,
                    onDismissRequest = { fromUomListExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.foodUoms.get(uiState.selectedFood)?.forEachIndexed { index, uom ->
                        DropdownMenuItem(
                            text = { Text(text = uom) },
                            onClick = {
                                fromUomListExpanded = false
                                viewModel.updateSelectedUomIndex(index)
                            })

                    }
                }
            }
        }


       /* Divider(thickness = 2.dp)

        Button(onClick = { viewModel.getAvailableFoods() }) {
            Text("get list of available foods")
        }
        Text(text = "All available foods ${uiState.availableFoods}")
        Divider(thickness = 2.dp)

        Button(onClick = { viewModel.getUomForGivenFood() }) {
            Text("get all Uoms for Flour")
        }
        Text(text = "All available uoms for ${uiState.food} are ${uiState.uoms}")
        Divider(thickness = 2.dp)

        Text(text = "sending Flour, 2 cup, want in grams")

        Button(onClick = { viewModel.getConversion() }) {
            Text(text = "Push to get answer")
        }

        Text(text = "Substance - ${uiState.food}")
        Text(text = "From unit - ${uiState.fromUnit}")
        Text(text = "From amount - ${uiState.fromAmount}")
        Text(text = "Target unit - ${uiState.targetUnit}")
        Text(text = "Target amount - ${uiState.targetAmount}")

*/
    }

}

@Preview
@Composable
fun PreviewMainPage() {
    ConversionAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConversionApp()
        }
    }
}
