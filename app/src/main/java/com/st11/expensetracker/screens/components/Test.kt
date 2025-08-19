//package com.st11.expensetracker.screens.components
//
//package com.st11.eventmarker.screens.components
//
//import android.Manifest
//import android.content.pm.PackageManager
//import android.os.Build
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.MenuAnchorType
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.core.content.ContextCompat
//import com.st11.eventmarker.R
//import com.st11.eventmarker.utils.DatePickerField
//import com.st11.eventmarker.utils.TimePickerField
//import com.st11.eventmarker.utils.addEventToCalendar
//import com.st11.eventmarker.viewmodel.EventViewModel
//import com.st11.eventmarker.viewmodel.NotificationPrefsViewModel
//import com.st11.eventmarker.viewmodel.NotificationViewModel
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import org.koin.androidx.compose.koinViewModel
//import java.time.LocalDateTime
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditablePopup(
////    initialText: String,
//    onDismiss: () -> Unit,
////    onSave: (String) -> Unit,
//    itemId: String
//    ,
//    eventDate: String,
//    eventStartTime: String,
//    eventEndTime: String,
//    eventTitle: String,
//    eventVenue: String,
//    eventPriority: String,
//    eventCategory: String,
//    noteDescription: String
//) {
//
//    val backgroundColor = colorResource(id = R.color.seina)
//
//    var expanded by remember { mutableStateOf(false) }
//    var expanded01 by remember { mutableStateOf(false) }
//
//    var title by remember { mutableStateOf(eventTitle) }
//    var venue by remember { mutableStateOf(eventVenue) }
//    var eventDescription by remember { mutableStateOf(noteDescription) }
//    var priority by remember { mutableStateOf(eventPriority) }
//    var category by remember { mutableStateOf(eventCategory) }
//    var selectedDate by remember { mutableStateOf(eventDate) }
//    var startTime by remember { mutableStateOf(eventStartTime) }
//    var endTime by remember { mutableStateOf(eventEndTime) }
//
//
//    val notificationPrefsViewModel: NotificationPrefsViewModel = koinViewModel()
//    val userData by notificationPrefsViewModel.userData.collectAsState()
//
//    val eventViewModelPop: EventViewModel = koinViewModel()
//    val eventData  by eventViewModelPop.eventData.collectAsState()
//    val context = LocalContext.current
//
//
//    //user calender permission
//    val permissionState = remember { mutableStateOf(false) }
//    var showPermissionDialog by remember { mutableStateOf(false) }
//
//
//    // Check if permission is already granted
//    LaunchedEffect(Unit) {
//        val hasReadPermission = ContextCompat.checkSelfPermission(
//            context, Manifest.permission.READ_CALENDAR
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val hasWritePermission = ContextCompat.checkSelfPermission(
//            context, Manifest.permission.WRITE_CALENDAR
//        ) == PackageManager.PERMISSION_GRANTED
//
//        permissionState.value = hasReadPermission && hasWritePermission
//
//        // If not granted, show dialog once
//        if (!permissionState.value) {
//            delay(2000) // Add delay to prevent flicker
//            showPermissionDialog = true
//        }
//    }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val allGranted = permissions[Manifest.permission.READ_CALENDAR] == true &&
//                permissions[Manifest.permission.WRITE_CALENDAR] == true
//        permissionState.value = allGranted
//
//        if (!allGranted) {
//            Toast.makeText(context, "Calendar permissions denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    // Show rationale dialog before requesting
////        if (showPermissionDialog) {
//    if (showPermissionDialog && !permissionState.value) {
//        AlertDialog(
//            onDismissRequest = { showPermissionDialog = false },
//            title = { Text("Calendar Access Needed") },
//            text = { Text("We want to access your calendar to mark this event on your calendar.") },
//            confirmButton = {
//                TextButton(onClick = {
//                    showPermissionDialog = false
////                    permissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
//                    permissionLauncher.launch(
//                        arrayOf(
//                            Manifest.permission.READ_CALENDAR,
//                            Manifest.permission.WRITE_CALENDAR
//                        )
//                    )
//                }) {
//                    Text("Allow")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = {
//                    showPermissionDialog = false
//                }) {
//                    Text("Deny")
//                }
//            }
//        )
//    }
//
//
//
//
//    // ✅ 1. Get a coroutine scope tied to the composable's lifecycle
//    val scope = rememberCoroutineScope()
//    val eventViewModel: EventViewModel = koinViewModel()
//
//    val viewModel: NotificationViewModel = koinViewModel()
//
//    val priorityType = listOf(
//        "high", "low"
//    )
//
//    val categoryType = listOf(
//        "other",
//        "Doctor's Appointment",
//        "Gym Session",
//        "Yoga Class",
//        "Meeting",
//        "Work Deadline",
//        "Project Review",
//        "Conference",
//        "Team Standup",
//        "Training Session",
//        "Workshop",
//        "Interview",
//        "Personal Errand",
//        "Family Event",
//        "Birthday",
//        "Anniversary",
//        "Social Gathering",
//        "Dinner Plan",
//        "Vacation",
//        "Travel",
//        "Flight Schedule",
//        "Hotel Check-in",
//        "School Event",
//        "Parent-Teacher Meeting",
//        "Exam",
//        "Class",
//        "Webinar",
//        "Religious Event",
//        "Prayer Time",
//        "Medication Reminder",
//        "Bill Payment",
//        "Subscription Renewal",
//        "Shopping",
//        "Car Service",
//        "Pet Care",
//        "Cleaning Schedule",
//        "House Maintenance",
//        "Fitness Challenge",
//        "Sports Practice",
//        "Game Night",
//        "Concert",
//        "Movie Night",
//        "Networking Event"
//    )
//
////    title = eventTitle
////    venue = eventVenue
////    eventDescription = noteDescription
////    priority = eventPriority
////    category = eventCategory
////    selectedDate = eventDate
////    startTime = eventStartTime
////    endTime = eventEndTime
//
//
//    Dialog(onDismissRequest = { onDismiss() }) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            tonalElevation = 8.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Column(
//                Modifier
//                    .padding(16.dp)
//                    .imePadding()
//                    .verticalScroll(rememberScrollState()), // Adjust for keyboard
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//
//            ) {
//                Text(text = "Edit", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//
////                OutlinedTextField(
////                    value = text,
////                    onValueChange = { text = it },
////                    label = { Text("Edit text") },
////                    modifier = Modifier.fillMaxWidth(),
////                    singleLine = false,
////                    maxLines = 5
////                )
//
//
//                OutlinedTextField(
//                    value = title,
//                    onValueChange = { title = it },
//                    label = { Text("Event Title *") },
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
//                )
//
//
//                OutlinedTextField(
//                    value = venue,
//                    onValueChange = { venue = it },
//                    label = { Text("Venue e.g medina hall") },
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
//                )
//
//
//
//                ExposedDropdownMenuBox(
//                    expanded = expanded,
//                    onExpandedChange = { expanded = !expanded }
//                ) {
//                    OutlinedTextField(
//                        value = priority,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("Event Priority *") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
////                            .menuAnchor(),
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                        },
//                        singleLine = true,
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
//                        onDismissRequest = { expanded = false },
//                        modifier = Modifier
//                            .background(Color.White) // ✅ White background for the dropdown menu
//                    ) {
//                        priorityType.forEach { selectionOption ->
//                            DropdownMenuItem(
//                                text = { Text(selectionOption, color = Color.Black) }, // ✅ Black text
//                                onClick = {
//                                    priority = selectionOption
//                                    expanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//
//                ExposedDropdownMenuBox(
//                    expanded = expanded01,
//                    onExpandedChange = { expanded01 = !expanded01 }
//                ) {
//                    OutlinedTextField(
//                        value = category,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("Category") },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded01)
//                        },
//                        singleLine = true,
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
//                        expanded = expanded01,
//                        onDismissRequest = { expanded01 = false },
//                        modifier = Modifier
//                            .background(Color.White) // ✅ White background for the dropdown menu
//                    ) {
//                        categoryType.forEach { selectionOption ->
//                            DropdownMenuItem(
//                                text = { Text(selectionOption, color = Color.Black) }, // ✅ Black text
//                                onClick = {
//                                    category = selectionOption
//                                    expanded01 = false
//                                }
//                            )
//                        }
//                    }
//                }
////
////                DatePickerField(label = "Select a date") { date ->
////                    selectedDate = date
////                }
//
//                DatePickerField(
//                    label = "Select a date",
//                    value = selectedDate, // <-- This is from your parent state
//                    onDateSelected = { date -> selectedDate = date }
//                )
//
//
//
//                TimePickerField(
//                    label = "Start Time *",
//                    value = startTime,
//                    onTimeSelected = { selected -> startTime = selected }
//                )
//
//                TimePickerField(
//                    label = "End Time *",
//                    value = endTime,
//                    onTimeSelected = { selected -> endTime = selected }
//                )
//
//
////                // ✅ Start Time Picker
////                TimePickerField(label = "Start Time *") { selected ->
////                    startTime = selected
////                }
////
////                // ✅ End Time Picker
////                TimePickerField(label = "End Time *") { selected ->
//////                    endTime = selected
////                    endTime = if (endTime.isBlank()){
////                        eventEndTime
////                    }else{
////                        selected
////                    }
////
////                }
//
//
//                OutlinedTextField(
//                    value = eventDescription,
//                    onValueChange = { eventDescription = it },
//                    label = { Text("short Notes") },
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
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    TextButton(onClick = onDismiss) {
//                        Text("Cancel")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = {
////                        onSave(text)
//                        scope.launch {
//                            if (title.isNotEmpty() && selectedDate.isNotEmpty() && startTime.isNotEmpty() && priority.isNotEmpty() && category.isNotEmpty()) {
//                                eventViewModel.updateEventsDetails(
//                                    itemId,
//                                    title,
//                                    venue,
//                                    priority,
//                                    category,
//                                    eventDescription,
//                                    selectedDate
//                                )
//
//                                // ✅ Add to calendar only if permission granted
//                                if (permissionState.value) {
//                                    addEventToCalendar(
//                                        context = context,
//                                        title = title,
//                                        date = selectedDate,
//                                        startTime = startTime,
//                                        endTime = endTime,
//                                        location = venue
//                                    )
//                                } else {
//                                    Toast.makeText(context, "Event saved but calendar permission not granted", Toast.LENGTH_SHORT).show()
//                                }
//
//                                if(userData.isNotificationEnabled) {
//                                    // 2. 🛡️ Now, attempt the risky notification scheduling inside a try-catch block.
//                                    try {
//                                        val (year, month, day) = selectedDate.split("-")
//                                            .map { it.toInt() }
//                                        val (hour, minute) = startTime.split(":").map { it.toInt() }
//
//                                        val dateTime =
//                                            LocalDateTime.of(year, month, day, hour, minute)
//
//                                        val granted =
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                                                ContextCompat.checkSelfPermission(
//                                                    context,
//                                                    Manifest.permission.POST_NOTIFICATIONS
//                                                ) == PackageManager.PERMISSION_GRANTED
//                                            } else true
//
//                                        if (granted) {
////                                        viewModel.scheduleEventNotification(context, dateTime, "", eventTitle)
//                                            viewModel.scheduleUserNotification(
//                                                context = context,
//                                                title = "Your upcoming activities! reminder",
//                                                message = "$title on $selectedDate from $startTime to $endTime at " +
//                                                        (if (venue.isNullOrEmpty()) "Venue not set" else venue) +
//                                                        ". Don't forget!",
//                                                year = year,
//                                                month = month,
//                                                day = day,
//                                                hour = hour,
//                                                minute = minute
//                                            )
//
//
//                                        }
//
//                                    } catch (e: Exception) {
//                                        // If parsing fails, log the error instead of crashing!
//                                        Log.e(
//                                            "AddEventScreen",
//                                            "Failed to parse date/time and schedule notification.",
//                                            e
//                                        )
//                                        // Optionally, inform the user with a Toast
//                                        Toast.makeText(
//                                            context,
//                                            "Event saved, but failed to set reminder.",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                }
//
//                                onDismiss()
//
//                            }else {
//                                Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
//                            }
//
//                        }
//
//                    }) {
//                        Text("Save")
//                    }
//                }
//            }
//        }
//    }
//}