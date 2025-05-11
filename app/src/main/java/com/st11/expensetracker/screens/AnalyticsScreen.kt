package com.st11.expensetracker.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.st11.expensetracker.R
import com.st11.expensetracker.utils.DynamicStatusBar
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import compose.icons.fontawesomeicons.solid.ArrowRight
import compose.icons.fontawesomeicons.solid.MoneyCheck
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.Users
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import kotlin.random.Random




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.light_green)
    DynamicStatusBar(backgroundColor)

    val searchQuery = remember { mutableStateOf("") }


    val cardData = listOf(
        CardInfo("Total Spend", "KES 8,000", R.drawable.expenses),
        CardInfo("total Expenses This Month", "KES 390", R.drawable.moneybag),
        CardInfo("Total Wishlist", "KES 12,000", R.drawable.wishlist),
        CardInfo("Saved", "KES 4,000", R.drawable.budget01),
    )








    val currentMonth = remember { mutableStateOf(YearMonth.now()) }

    val allData = remember {
        // Replace this with actual data from DB or API
        mapOf(
            YearMonth.of(2025, 5) to List(31) { (it + 1).toString() to Random.nextFloat() * 100 },
            YearMonth.of(2025, 4) to List(30) { (it + 1).toString() to Random.nextFloat() * 100 },
            YearMonth.of(2025, 3) to List(31) { (it + 1).toString() to Random.nextFloat() * 100 }
        )
    }

    val daysInMonth = currentMonth.value.lengthOfMonth()
    val expenseData = allData[currentMonth.value] ?: List(daysInMonth) { (it + 1).toString() to 0f }


    val maxAmount = expenseData.maxOfOrNull { it.second } ?: 1f
    val chartHeight = 150.dp // total height allocated for bars


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard Overview", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.light_bg_color))
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {


            }



            Spacer(modifier = Modifier.height(16.dp))

//
//            // No data available at the initial display
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "No Data Available",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray
//                )
//
//            }
//            Column(modifier = Modifier.fillMaxSize()) {
//                // Top controls
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = {
//                        currentMonth.value = currentMonth.value.minusMonths(1)
//                    }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
//                            contentDescription = "Previous Month",
////                            tint = if (isSelected) selectedColor else unselectedColor,
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//
//                    Text(
//                        text = currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.value.year,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    )
//
//                    IconButton(onClick = {
//                        currentMonth.value = currentMonth.value.plusMonths(1)
//                    }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
//                            contentDescription = "Next Month",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                }
//
//                // Bar chart
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(240.dp),
//                    contentPadding = PaddingValues(horizontal = 16.dp)
//                ) {
//                    items(expenseData) { (day, amount) ->
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier
//                                .width(40.dp)
//                                .padding(horizontal = 4.dp)
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .height((amount).dp)
//                                    .width(20.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .background(Color(0xFF4CAF50))
//                            )
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text(day, fontSize = 10.sp)
//                        }
//                    }
//                }
//            }

//            Column(modifier = Modifier.fillMaxSize()) {
//
//                // Top controls
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = {
//                        currentMonth.value = currentMonth.value.minusMonths(1)
//                    }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
//                            contentDescription = "Previous Month",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//
//                    Text(
//                        text = currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.value.year,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    )
//
//                    IconButton(onClick = {
//                        currentMonth.value = currentMonth.value.plusMonths(1)
//                    }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
//                            contentDescription = "Next Month",
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                }
//
//                // Bar chart
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(chartHeight + 50.dp), // extra space for labels
//                    contentPadding = PaddingValues(horizontal = 16.dp)
//                ) {
//                    items(expenseData) { (day, amount) ->
//                        val barHeight = (amount / maxAmount) * chartHeight.value
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier
//                                .width(40.dp)
//                                .padding(horizontal = 4.dp),
//                            verticalArrangement = Arrangement.Bottom
//                        ) {
//                            Text(
//                                text = amount.toInt().toString(),
//                                fontSize = 10.sp,
//                                modifier = Modifier.padding(bottom = 4.dp)
//                            )
//
//                            Box(
//                                modifier = Modifier
//                                    .height(barHeight.dp)
//                                    .width(20.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .background(Color(0xFF4CAF50))
//                            )
//
//                            Spacer(modifier = Modifier.height(4.dp))
//
//                            Text(day, fontSize = 10.sp)
//                        }
//                    }
//                }
//            }



            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cardData) { card ->
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.light_green)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = card.iconRes),
                                contentDescription = card.title,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(8.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = card.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Text(
                                    text = card.amount,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }





            Column(modifier = Modifier.fillMaxSize()) {

                // Top controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentMonth.value = currentMonth.value.minusMonths(1)
                    }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                            contentDescription = "Previous Month",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.value.year,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    IconButton(onClick = {
                        currentMonth.value = currentMonth.value.plusMonths(1)
                    }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowRight,
                            contentDescription = "Next Month",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Bottom-origin Bar Chart
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(chartHeight + 50.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(expenseData) { (day, amount) ->
                        val barHeight = (amount / maxAmount) * chartHeight.value

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(40.dp)
                                .padding(horizontal = 4.dp)
                        ) {

                            Text(
                                text = "Ksh ${amount.toInt().toString()}",
                                fontSize = 10.sp,
                                modifier = Modifier.graphicsLayer {
                                    rotationZ = -80f // Rotate counter-clockwise
                                }
                            )


                            Spacer(modifier = Modifier.height(4.dp))

                            // Box to align bar from bottom
                            Box(
                                modifier = Modifier
                                    .height(chartHeight)
                                    .width(20.dp),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(barHeight.dp)
                                        .width(20.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFF4CAF50))
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(day, fontSize = 10.sp)
//                            Text(text = amount.toInt().toString(), fontSize = 10.sp)
                        }
                    }
                }
            }
            
            
            
            
            
            /////

        }
    }
}
data class CardInfo(val title: String, val amount: String, val iconRes: Int)

@Preview(showBackground = true)
@Composable
fun AnalyticsScreenPreview() {
    AnalyticsScreen(navController = rememberNavController())
}