package com.st11.expensetracker.screens.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.st11.expensetracker.R
import com.st11.expensetracker.model.ExpenseEntity
import com.st11.expensetracker.viewmodel.ExpenseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensePopup(onDismiss: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var expenseDescription by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var expanded02 by remember { mutableStateOf(false) }


    val backgroundColor = colorResource(id = R.color.light_green)
    val context = LocalContext.current

    val expenseViewModel: ExpenseViewModel = koinViewModel()

    // Category types
    val categoryType = listOf(
        "Other", "Food",
        "Transport", "Bills",
        "Shopping", "Entertainment",
        "Health", "Education",
        "Groceries", "Travel",
        "Personal Care", "Utilities",
        "Gifts & Donations", "Rent",
        "Subscriptions", "Insurance", "Taxes", "Pets", "Savings"
    )

    val paymentType = listOf(
        "Other", "M-pesa", "Bank", "Cash", "PayPal"
        )





//    AlertDialog(
//        onDismissRequest = { onDismiss() },
//        title = { Text(text = "Add an Expense") },
//        text = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
//            ) {
//                Spacer(modifier = Modifier.height(24.dp))
//
//                OutlinedTextField(
//                    value = amount,
//                    onValueChange = { amount = it },
//                    label = { Text("Amount") },
//                    modifier = Modifier.fillMaxWidth(),
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
//                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
//                        focusedBorderColor = backgroundColor,
//                        unfocusedBorderColor = Color.Gray,
//                        focusedLabelColor = backgroundColor,
//                        cursorColor = backgroundColor
//                    ),
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                ExposedDropdownMenuBox(
//                    expanded = expanded,
//                    onExpandedChange = { expanded = !expanded }
//                ) {
//                    TextField(
//                        value = category,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("category") },
//                        modifier = Modifier
//                            .menuAnchor()
//                            .fillMaxWidth(),
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                        },
//                        singleLine = true,
////                        colors = ExposedDropdownMenuDefaults.textFieldColors()
//                        colors = OutlinedTextFieldDefaults.colors(
//                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
//                            focusedContainerColor = Color.White.copy(alpha = 0.95f),
//                            focusedBorderColor = backgroundColor,
//                            unfocusedBorderColor = Color.Gray,
//                            focusedLabelColor = backgroundColor,
//                            cursorColor = backgroundColor
//                        )
//                    )
//
//                    ExposedDropdownMenu(
//                        expanded = expanded,
//                        onDismissRequest = { expanded = false }
//                    ) {
//                        categoryType.forEach { selectionOption ->
//                            DropdownMenuItem(
//                                text = { Text(selectionOption) },
//                                onClick = {
//                                    category = selectionOption
//                                    expanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                OutlinedTextField(
//                    value = expenseDescription,
//                    onValueChange = { expenseDescription = it },
//                    label = { Text("Expense Short Description") },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(min = 100.dp, max = 200.dp) // Adjust height for ~4 lines
//                        .verticalScroll(rememberScrollState()),
//
//                    colors = OutlinedTextFieldDefaults.colors(
//                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
//                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
//                        focusedBorderColor = backgroundColor,
//                        unfocusedBorderColor = Color.Gray,
//                        focusedLabelColor = backgroundColor,
//                        cursorColor = backgroundColor
//                    ),
//                    singleLine = false,
//                    maxLines = 4
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                ExposedDropdownMenuBox(
//                    expanded = expanded02,
//                    onExpandedChange = { expanded02 = !expanded02 }
//                ) {
//                    TextField(
//                        value = paymentMethod,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("payment Method") },
//                        modifier = Modifier
//                            .menuAnchor()
//                            .fillMaxWidth(),
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded02)
//                        },
//                        singleLine = true,
////                        colors = ExposedDropdownMenuDefaults.textFieldColors()
//                        colors = OutlinedTextFieldDefaults.colors(
//                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
//                            focusedContainerColor = Color.White.copy(alpha = 0.95f),
//                            focusedBorderColor = backgroundColor,
//                            unfocusedBorderColor = Color.Gray,
//                            focusedLabelColor = backgroundColor,
//                            cursorColor = backgroundColor
//                        )
//                    )
//
//                    ExposedDropdownMenu(
//                        expanded = expanded02,
//                        onDismissRequest = { expanded02 = false }
//                    ) {
//                        paymentType.forEach { selectionOption ->
//                            DropdownMenuItem(
//                                text = { Text(selectionOption) },
//                                onClick = {
//                                    paymentMethod = selectionOption
//                                    expanded02 = false
//                                }
//                            )
//                        }
//                    }
//                }
//
//
//            }
//        },
//        confirmButton = {
//
//            TextButton(onClick = { /* Handle submission */
//                if (amount.isNotBlank() && expenseDescription.isNotBlank() && category.isNotBlank() && paymentMethod.isNotBlank()) {
//                    CoroutineScope(Dispatchers.Main).launch {
//                        expenseViewModel.insertExpense(
//                            ExpenseEntity(
//                                expenseId = generateSixDigitRandomNumber().toString(),
//                                expenseAmount = amount.toFloat(),
//                                expenseCategory = category,
//                                expenseDescription = expenseDescription,
//                                paymentMode = paymentMethod
//                            )
//                        )
//                    }
//                    onDismiss()
//                }else{
//                    Toast.makeText(context, "All fields are required", Toast.LENGTH_LONG).show()
//                }
//            }) {
//                Text("Add", color = colorResource(R.color.green))
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = { onDismiss() }) {
//                Text("Cancel", color = colorResource(R.color.red))
//            }
//        }
//    )

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()), // Adjust for keyboard
                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {
                Text(text = "Add An Expense", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }

                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("category") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        singleLine = true,
//                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White.copy(alpha = 0.95f),
                            focusedBorderColor = backgroundColor,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = backgroundColor,
                            cursorColor = backgroundColor
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        categoryType.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    category = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = expenseDescription,
                    onValueChange = { expenseDescription = it },
                    label = { Text("Expense Short Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 200.dp) // Adjust height for ~4 lines
                        .verticalScroll(rememberScrollState()),

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = backgroundColor,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = backgroundColor,
                        cursorColor = backgroundColor
                    ),
                    singleLine = false,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded02,
                    onExpandedChange = { expanded02 = !expanded02 }
                ) {
                    OutlinedTextField(
                        value = paymentMethod,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("payment Method") },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded02)
                        },
                        singleLine = true,
//                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                            focusedContainerColor = Color.White.copy(alpha = 0.95f),
                            focusedBorderColor = backgroundColor,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = backgroundColor,
                            cursorColor = backgroundColor
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded02,
                        onDismissRequest = { expanded02 = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        paymentType.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    paymentMethod = selectionOption
                                    expanded02 = false
                                }
                            )
                        }
                    }
                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {onDismiss()},
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Gray // text color
                        )
                    ) {
                        Text("Cancel")
                    }

                    // Save Button (Green background)
                    Button(
                        onClick = {
                            if (amount.isNotBlank() && expenseDescription.isNotBlank() && category.isNotBlank() && paymentMethod.isNotBlank()) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    expenseViewModel.insertExpense(
                                        ExpenseEntity(
                                            expenseId = generateSixDigitRandomNumber().toString(),
                                            expenseAmount = amount.toFloat(),
                                            expenseCategory = category,
                                            expenseDescription = expenseDescription,
                                            paymentMode = paymentMethod
                                        )
                                    )
                                }
                                onDismiss()
                            }else{
                                Toast.makeText(context, "All fields are required", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50), // Green background
                            contentColor = Color.White          // White text
                        )
                    ) {
                        Text("Save")
                    }

                }
            }
        }
    }





}

fun generateSixDigitRandomNumber(): Int {
    return Random.nextInt(100000, 1000000)  // Generates a random number between 100000 (inclusive) and 1000000 (exclusive)
}