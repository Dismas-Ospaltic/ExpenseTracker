package com.st11.expensetracker.screens



import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.st11.expensetracker.R
import com.st11.expensetracker.utils.DynamicStatusBar
import com.st11.expensetracker.viewmodel.IntervalViewModel
import com.st11.expensetracker.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel




//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun CreditAuthorScreen(navController: NavController) {
//
//    val backgroundColor = colorResource(id = R.color.light_green)
//    DynamicStatusBar(backgroundColor)  // ✅ Apply dynamic status bar settings
//    val context = LocalContext.current
//
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("About & Credits/Acknowledgement", color = Color.White) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = backgroundColor
//                )
//            )
//        },
//        containerColor = colorResource(id = R.color.light_bg_color)
//    ) { paddingValues ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(horizontal = 16.dp, vertical = 24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "About the app",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//
//            Text(
//                text = "This app helps you track daily expenses and plan future goals - all in one place. Stay organized, focused, and in control of your finances.",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//
//
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(
//                text = "Credits & Acknowledgements",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )
//        val iconCredits = listOf(
//            "App icon was designed by zafdesign from Flaticon",
//
//            "Analytics Screen image icons:",
//            "-Total Spend icon image designed by Parzival’ 1997 from Flaticon",
//            "-Total expenses this month icon image designed by Freepik from Flaticon",
//            "-Total Wishlist icon image designed by nawicon from Flaticon",
//            "-Total Wishlist No. icon image designed by nawicon from Flaticon",
//        )
//
//
//        iconCredits.forEach { credit ->
//            Text(
//                text = credit,
//                style = if (credit.endsWith(":"))
//                    MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//                else
//                    MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(vertical = 4.dp)
//            )
//        }
//
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = "Visit Flaticon: www.flaticon.com",
//            style = MaterialTheme.typography.bodySmall.copy(color = Color.Blue),
//            modifier = Modifier.clickable {
//                val url = "https://www.flaticon.com"
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                context.startActivity(intent)
//            }
//        )
//
//        }
//    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditAuthorScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.light_green)
    DynamicStatusBar(backgroundColor) // ✅ Keeps status bar consistent
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("About & Credits", color = Color.White) },
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
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()) // ✅ Scrollable content
        ) {
            // ✅ About Section
            Text(
                text = "About This App",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Take control of your finances with an easy, powerful tool for tracking daily expenses and planning future goals. Stay organized, focused, and in control.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Credits Section
            Text(
                text = "Credits & Acknowledgements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val iconCredits = listOf(
//                "App icon designed by zafdesign from Flaticon",
//                "Analytics icons:",
//                "- Total Spend icon by Parzival’ 1997",
//                "- Monthly Expense icon by Freepik",
//                "- Wishlist icons by nawicon"
                "App icon was designed by zafdesign from Flaticon",

                "Analytics Screen image icons:",
                "-Total Spend icon image designed by Parzival’ 1997 from Flaticon",
                "-Total expenses this month icon image designed by Freepik from Flaticon",
                "-Total Wishlist icon image designed by nawicon from Flaticon",
                "-Total Wishlist No. icon image designed by nawicon from Flaticon",
            )

            iconCredits.forEach { credit ->
                Text(
                    text = credit,
                    style = if (credit.endsWith(":"))
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    else
                        MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ External link
            Text(
                text = "Visit Flaticon: www.flaticon.com",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable {
                        val url = "https://www.flaticon.com"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                    .padding(vertical = 4.dp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CreditAuthorScreenPreview() {
    CreditAuthorScreen(navController = rememberNavController())
}
