package com.st11.expensetracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    // Use a simple delay to simulate a splash screen
    androidx.compose.runtime.LaunchedEffect(Unit) {
        delay(2000) // Simulate splash screen duration
        onNavigate() // Navigate after the delay
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)

        ) {
            Image(
                painter = painterResource(id = com.st11.expensetracker.R.drawable.accountingnew),
                contentDescription = "Splash Image",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Welcome to Expense Tracker", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }
    }
}