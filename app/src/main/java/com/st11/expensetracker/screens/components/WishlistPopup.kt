package com.st11.expensetracker.screens.components


import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.st11.expensetracker.R
import com.st11.expensetracker.model.WishlistEntity
import com.st11.expensetracker.viewmodel.WishlistViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Calendar
import compose.icons.fontawesomeicons.solid.ChartBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistPopup(onDismiss: () -> Unit) {
    var estimatedAmount by remember { mutableStateOf("") }
    var itemName by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var itemDescription by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }


    val backgroundColor = colorResource(id = R.color.light_green)
    val context = LocalContext.current


    val wishlistViewModel: WishlistViewModel = koinViewModel()
//    val wishlist by wishlistViewModel.wishs.collectAsState()


    val priorityType = listOf(
        "high", "medium", "low"
    )

    val calendar = Calendar.getInstance()

    fun showDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, day)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                date = dateFormat.format(selectedDate.time) // Format date to "YYYY-MM-DD"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Add To Wishlist") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()) // ✅ Enable scrolling
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
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
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = estimatedAmount,
                    onValueChange = { estimatedAmount = it },
                    label = { Text("Estimated Amount") },
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


                // Date Picker TextField
                TextField(
                    value = date,
                    onValueChange = {},
                    label = { Text("Target Date") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker() }) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Calendar,
                                contentDescription = "chart",
                                tint = backgroundColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                    focusedBorderColor = backgroundColor,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = backgroundColor,
                    cursorColor = backgroundColor
                )
                )


                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = priority,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Priority") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
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
                        onDismissRequest = { expanded = false }
                    ) {
                        priorityType.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    priority = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = itemDescription,
                    onValueChange = { itemDescription = it },
                    label = { Text("item Short Description") },
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

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Short Note(optional)") },
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





            }
        },
        confirmButton = {

            TextButton(onClick = { /* Handle submission */
                if (estimatedAmount.isNotBlank() && itemDescription.isNotBlank() && priority.isNotBlank() && itemName.isNotBlank()) {

                    CoroutineScope(Dispatchers.Main).launch {
                       wishlistViewModel.insertWish(
                           WishlistEntity(
                               wishId = generateSixDigitRandomNumberWishlist().toString(),
                               itemName = itemName,
                               estimateAmount = estimatedAmount.toFloat(),
                               priority = priority,
                               targetDate = date,
                               wishDescription = itemDescription,
                               wishNote = notes
                           )
                       )

                    }

                    onDismiss()
                }else{
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Add", color = colorResource(R.color.green))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel", color = colorResource(R.color.red))
            }
        }
    )
}

fun generateSixDigitRandomNumberWishlist(): Int {
    return Random.nextInt(100000, 1000000)  // Generates a random number between 100000 (inclusive) and 1000000 (exclusive)
}