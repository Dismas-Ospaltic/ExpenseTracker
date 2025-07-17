package com.st11.expensetracker.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.st11.expensetracker.R
import com.st11.expensetracker.utils.DynamicStatusBar
import com.st11.expensetracker.viewmodel.IntervalViewModel
import com.st11.expensetracker.viewmodel.MainViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Calendar
import compose.icons.fontawesomeicons.solid.Users
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
fun NotificationPreferenceScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.light_green)
    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings

    val intervalViewModel: IntervalViewModel = koinViewModel()
    val viewModel: MainViewModel = koinViewModel()
    val hasWorkerStated by intervalViewModel.hasWorkerStarted.collectAsState(initial = false)

    val intervals = listOf(
        "1 hr" to 1L,
        "6 hr" to 6L,
        "8 hrs" to 8L

    )

    var selectedIntervalLabel by remember { mutableStateOf(intervals[0].first) }
    // Retrieve the selected interval in hours (Long)
    val selectedIntervalHours = intervals.first { it.first == selectedIntervalLabel }.second
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notification Settings", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Notification Interval",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Vertical list of radio buttons
            intervals.forEach { (label, _) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedIntervalLabel = label }
                ) {
                    RadioButton(
                        selected = selectedIntervalLabel == label,
                        onClick = { selectedIntervalLabel = label },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.light_green)
                        )
                    )
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    //save to preferences
                    intervalViewModel.saveTimeInterval(selectedIntervalHours)
                    if (hasWorkerStated){
                        viewModel.stopReminderWorker()
//                        intervalViewModel.markWorkerStopped()
                        viewModel.startReminderWorker(selectedIntervalHours)
                        intervalViewModel.markWorkerStarted()
                    }else{
                        viewModel.startReminderWorker(selectedIntervalHours)
                    }


                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text("Save", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NotificationPreferenceScreenPreview() {
    NotificationPreferenceScreen(navController = rememberNavController())
}
