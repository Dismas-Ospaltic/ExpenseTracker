package com.st11.expensetracker.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.expensetracker.R
import com.st11.expensetracker.utils.DynamicStatusBar
import com.st11.expensetracker.viewmodel.CurrencyViewModel
import com.st11.expensetracker.viewmodel.ExpenseViewModel
import com.st11.expensetracker.viewmodel.WishlistViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.AngleLeft
import compose.icons.fontawesomeicons.solid.AngleRight
import compose.icons.fontawesomeicons.solid.ArrowLeft
import compose.icons.fontawesomeicons.solid.ArrowRight
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.light_green)
    DynamicStatusBar(backgroundColor)

    val searchQuery = remember { mutableStateOf("") }

    val expenseViewModel: ExpenseViewModel = koinViewModel()
    val dailyExpenseTotals by expenseViewModel.dailyExpenseTotals.collectAsState()

    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()

    val totalExpense by expenseViewModel.totalExpense.collectAsState(initial = 0.0f)
    val totalMonthExpense by expenseViewModel.totalMonthExpense.collectAsState(initial = 0.0f)

    val wishlistViewModel: WishlistViewModel = koinViewModel()
    val wishlistTotal by wishlistViewModel.totalWishlistAmount.collectAsState(initial = 0.0f)
    val totalNoWishlist by wishlistViewModel.totalNoWishlist.collectAsState(initial = 0)



    val currentYearMonth = YearMonth.now().toString() // "2025-05"
//    val totalForThisMonth = expenseDao.getMonthlyTotalExpense(currentYearMonth)

    // 🔍 Debug with LaunchedEffect
    LaunchedEffect(Unit) {
        expenseViewModel.getAllTotalExpense()
        expenseViewModel.getMonthlyTotalExpense(currentYearMonth)
        wishlistViewModel.getAllTotalWishlistAmount()
        wishlistViewModel.getAllTotalWishlist()
    }



    val cardData = listOf(
        CardInfo("Total Spend", "${currency.userCurrency} $totalExpense", R.drawable.moneyout),
        CardInfo("total Expenses This Month", "${currency.userCurrency} $totalMonthExpense", R.drawable.moneybag),
        CardInfo("Total Wishlist", "${currency.userCurrency} $wishlistTotal", R.drawable.expense),
        CardInfo("Wishlist No.", "$totalNoWishlist", R.drawable.expense),
    )




    val currentMonth = remember { mutableStateOf(YearMonth.now()) }


    val allData = remember(dailyExpenseTotals) {
        dailyExpenseTotals
            .mapNotNull { item ->
                try {
                    val parts = item.expenseDate.split("-") // expecting format "YYYY-MM-dd"
                    val year = parts[0].toInt()
                    val month = parts[1].toInt()
                    val day = parts[2].padStart(2, '0')
                    Triple(YearMonth.of(year, month), day, item.totalAmount)
                } catch (e: Exception) {
                    null // Skip invalid date
                }
            }
            .groupBy { it.first } // group by YearMonth
            .mapValues { (_, values) ->
                values.map { it.second to it.third } // List<Pair<String, Float>>
            }
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
                .padding(
                    //             paddingValues
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding() + 80.dp
                )
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
                                    color = Color.White
//                                    color = Color.Black
                                )
                                Text(
                                    text = card.amount,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
//                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }

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
//                // Bottom-origin Bar Chart
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(chartHeight + 50.dp),
//                    contentPadding = PaddingValues(horizontal = 8.dp)
//                ) {
//                    items(expenseData) { (day, amount) ->
//                        val barHeight = (amount / maxAmount) * chartHeight.value
//
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            modifier = Modifier
//                                .width(40.dp)
//                                .padding(horizontal = 4.dp)
//                        ) {
//
//                            Text(
//                                text = "${currency.userCurrency} ${amount.toInt()}",
//                                fontSize = 10.sp,
//                                modifier = Modifier.graphicsLayer {
//                                    rotationZ = -80f // Rotate counter-clockwise
//                                }
//                            )
//
//
//                            Spacer(modifier = Modifier.height(4.dp))
//
//                            // Box to align bar from bottom
//                            Box(
//                                modifier = Modifier
//                                    .height(chartHeight)
//                                    .width(20.dp),
//                                contentAlignment = Alignment.BottomCenter
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .height(barHeight.dp)
//                                        .width(20.dp)
//                                        .clip(RoundedCornerShape(4.dp))
//                                        .background(Color(0xFF4CAF50))
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(4.dp))
//                            Text(day, fontSize = 10.sp)
////                            Text(text = amount.toInt().toString(), fontSize = 10.sp)
//                        }
//                    }
//                }
//            }


            Column(modifier = Modifier.fillMaxSize()) {

                // Top controls (Previous / Next Month)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { currentMonth.value = currentMonth.value.minusMonths(1) }) {
                        Icon(imageVector = FontAwesomeIcons.Solid.AngleLeft, contentDescription = "Previous Month",tint = colorResource(id = R.color.light_green), modifier = Modifier.size(24.dp))
                    }

                    Text(
                        text = currentMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.value.year +" Amount is in: ${currency.userCurrency}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.dark)
                    )

                    IconButton(onClick = { currentMonth.value = currentMonth.value.plusMonths(1) }) {
                        Icon(imageVector = FontAwesomeIcons.Solid.AngleRight, contentDescription = "Next Month", tint = colorResource(id = R.color.light_green), modifier = Modifier.size(24.dp))
                    }
                }

                // Chart with X & Y Axis and grid lines
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(chartHeight + 50.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val chartWidth = size.width
                        val chartHeightPx = size.height - 40.dp.toPx() // space for X labels
                        val step = chartHeightPx / 5

                        // Draw horizontal grid lines
                        for (i in 0..5) {
                            val y = chartHeightPx - (i * step)
                            drawLine(
                                color = Color.LightGray.copy(alpha = 0.3f),
                                start = Offset(0f, y),
                                end = Offset(chartWidth, y),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                    }

                    // Bar Chart
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(chartHeight + 40.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(expenseData) { (day, amount) ->
                            val barHeight = (amount / maxAmount) * chartHeight.value

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(40.dp)
                            ) {
                                Text(
                                    text = " ${amount.toInt()}", //${currency.userCurrency}
                                    fontSize = 9.sp,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    modifier = Modifier.graphicsLayer {
                                        rotationZ = -60f
                                    }
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .height(chartHeight)
                                        .width(24.dp),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(barHeight.dp)
                                            .width(24.dp)
                                            .background(
                                               colorResource(id = R.color.darkLight)
                                            )
//                                            .background(
//                                                Brush.verticalGradient(
//                                                    listOf(
//                                                        colorResource(id = R.color.dark),
//                                                        colorResource(id = R.color.darkLight)
//                                                    )
//                                                )
//                                            )
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text(day, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            }
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