package com.st11.expensetracker.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
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
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.st11.expensetracker.R
import com.st11.expensetracker.utils.DynamicStatusBar
import com.st11.expensetracker.utils.formatDate
import com.st11.expensetracker.utils.formatDateToReadable
import com.st11.expensetracker.viewmodel.CurrencyViewModel
import com.st11.expensetracker.viewmodel.WishlistViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.DollarSign
import compose.icons.fontawesomeicons.solid.MoneyCheck
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.Users
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WishlistScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.light_green)
    DynamicStatusBar(backgroundColor)

    val searchQuery = remember { mutableStateOf("") }

    val wishlistViewModel: WishlistViewModel = koinViewModel()
    val wishlist by wishlistViewModel.wishs.collectAsState()

    val currencyViewModel: CurrencyViewModel = koinViewModel()
    val currency by currencyViewModel.userData.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val selectedWishes = remember { mutableStateOf<Set<String>>(emptySet()) }





    // ✅ **Filter the list based on search query**
    val filteredWishlist = wishlist.filter {
        it.itemName.contains(searchQuery.value, ignoreCase = true) ||
                it.priority.contains(searchQuery.value, ignoreCase = true) ||
                it.wishDescription.contains(searchQuery.value, ignoreCase = true)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wishlist", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    if (selectedWishes.value.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .clickable { showDeleteDialog = true },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.DollarSign,
                                contentDescription = "money check",
                                tint = colorResource(id = R.color.dark),
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "Mark as purchased",
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.dark),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // AlertDialog logic
                        if (showDeleteDialog) {
                            AlertDialog(
                                onDismissRequest = { showDeleteDialog = false },
                                title = { Text("Confirm Purchase") },
                                text = { Text("Mark as Purchased?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        for (wishId in selectedWishes.value) {
                                            val wish = wishlist.find { it.wishId == wishId }
                                            val wishStatus = wish?.wishStatus
                                            if (wishStatus != "Purchased") {
                                            if (wish != null) {
                                                wishlistViewModel.updateWishStatus(
                                                    wishId,
                                                    "Purchased",
                                                    formatDate(System.currentTimeMillis())
                                                )
                                            }}
                                        }
                                        Toast.makeText(
                                            context,
                                            "Marked as Purchased for ${selectedWishes.value.size} wishes",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedWishes.value = emptySet()
                                        showDeleteDialog = false
                                    }) {
                                        Text(
                                            text = "Mark as Purchased",
                                            color = colorResource(id = R.color.red)
                                        )
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDeleteDialog = false }) {
                                        Text(
                                            text = "Cancel",
                                            color = colorResource(id = R.color.teal_700)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }



            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
//                 paddingValues
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

            // Search Field
            TextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                placeholder = { Text(text = "Search...") },
                leadingIcon = {

                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Show "No Data Available" if the list is empty initially or after filtering
            if (wishlist.isEmpty()) {
            // No data available at the initial display
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Data Available",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

                // No data available after search

        else if (filteredWishlist.isEmpty()){
                // No data available after search
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Results Found",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }else {


                for (index in filteredWishlist.indices) {
                    val item = filteredWishlist[index]
                    val hapticFeedback = LocalHapticFeedback.current
                    val isSelected = selectedWishes.value.contains(item.wishId)

                    val onClick = {
                        if (selectedWishes.value.isNotEmpty()) {
                            selectedWishes.value = selectedWishes.value.toMutableSet().apply {
                                if (contains(item.wishId)) remove(item.wishId) else add(item.wishId)
                            }
                        }
                    }

                    val onLongPress = {
                        selectedWishes.value = selectedWishes.value.toMutableSet().apply {
                            if (contains(item.wishId)) remove(item.wishId) else add(item.wishId)
                        }
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .combinedClickable(
                                onClick = onClick,
                                onLongClick = onLongPress
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        // Show checkbox only if selected
                        if (isSelected) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = { onLongPress() },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = colorResource(id = R.color.teal_700),
                                    uncheckedColor = colorResource(id = R.color.darkLight),
                                    checkmarkColor = colorResource(id = R.color.white)
                                )
                            )
                        }
                        Text(
                            text = "Target Date: ${formatDateToReadable(item.targetDate)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.light_green),
                            modifier = Modifier.align(Alignment.End).
                            padding(8.dp)
                        )
                        Text(
                            text = "Status: ${item.wishStatus}",
                            modifier = Modifier.align(Alignment.End).
                            padding(end = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(modifier = Modifier.padding(16.dp)) {


                            Text("Priority: ${item.priority}", color = colorResource(id = R.color.light_green))
                            Text("Item Name: ${item.itemName}")
                            Text("Estimated Price: ${currency.userCurrency} ${item.estimateAmount}")
                            Text("Description: ${item.wishDescription}")
                            Text("Notes: ${item.wishNote.ifBlank { "No note added" }}")
                        }
                    }
                }



////                repeat(10) { index ->
////                items(filteredPeople) { index, person ->
//                for (index in filteredWishlist.indices) {
//                    val hapticFeedback = LocalHapticFeedback.current
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                            .combinedClickable(
//                                onClick = onClick,
//                                onLongClick = {
//                                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
//                                    onLongPress()
//                                })
//                            .clickable {
//
//                            },
//                        shape = RoundedCornerShape(12.dp),
//                        colors = CardDefaults.cardColors(containerColor = Color.White)
//
//
//                    ) {
//                        Text(
//                            text = "Target Date: " + formatDateToReadable(wishlist[index].targetDate),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            modifier = Modifier.align(Alignment.End)
//                                .padding(8.dp),
//                            color = colorResource(id = R.color.light_green)
//                        )
//                        Text(
//                            text = "Status: ${wishlist[index].wishStatus}", modifier = Modifier.align(Alignment.End)
//                                .padding(end = 8.dp)
//                        )
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                if (isSelected) {
//                                    Checkbox(
//                                        checked = true,
//                                        onCheckedChange = { onLongPress() },
//                                        colors = CheckboxDefaults.colors(
//                                            checkedColor = colorResource(id = R.color.teal_700),
//                                            uncheckedColor = colorResource(id = R.color.darkLight),
//                                            checkmarkColor = colorResource(id = R.color.white)
//                                        )
//                                    )
//                                }
//                            Column(modifier = Modifier.weight(1f)) {
//                                Text(
//                                    text = "priority: ${wishlist[index].priority}",
//                                    fontSize = 12.sp,
//                                    color = colorResource(id = R.color.light_green)
//                                )
//                                Text(text = "Item Name: ${wishlist[index].itemName}")
//                                Text(text = "Estimated Price: ${currency.userCurrency} ${wishlist[index].estimateAmount}")
//                                Text(text = "Description: ${wishlist[index].wishDescription}")
//                                Text(text = "Notes: ${wishlist[index].wishNote.ifBlank { "No note added" }}")
//                            }
//                        }
//                        }
//                    }
//                }
            }
                }

            }

        }




@Preview(showBackground = true)
@Composable
fun WishlistScreenPreview() {
    WishlistScreen(navController = rememberNavController())
}