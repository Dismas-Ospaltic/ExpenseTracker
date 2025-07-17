package com.st11.expensetracker

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.Window
import android.view.WindowInsetsController
import androidx.compose.ui.res.painterResource
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.st11.expensetracker.navigation.AppNavHost
import com.st11.expensetracker.navigation.Screen
import com.st11.expensetracker.screens.components.HomeFAB
import com.st11.expensetracker.screens.components.WishlistFAB

import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Briefcase
import compose.icons.fontawesomeicons.solid.ChartBar
import compose.icons.fontawesomeicons.solid.Cog
import compose.icons.fontawesomeicons.solid.Heart
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.List
import compose.icons.fontawesomeicons.solid.MoneyCheck
import compose.icons.fontawesomeicons.solid.Qrcode
import compose.icons.fontawesomeicons.solid.Store
import compose.icons.fontawesomeicons.solid.User
import compose.icons.fontawesomeicons.solid.Users
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure full-screen layout
        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            val navController = rememberAnimatedNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Define screens where the bottom bar should be hidden
            val hideBottomBarScreens = listOf(Screen.Splash.route,Screen.Onboarding.route, Screen.SelectCurrency.route, Screen.NotificationPref.route)

            Scaffold(
                bottomBar = {
//                    BottomNavigationBar(navController)
                    if (currentRoute !in hideBottomBarScreens) {
                        BottomNavigationBar(navController)
                    }
                },
                floatingActionButton = {
                    if (currentRoute == Screen.Home.route) { // Show FAB only on Home
                        HomeFAB(navController = navController)
                    }
                    if (currentRoute == Screen.Wishlist.route) { // Show FAB only on Home
                        WishlistFAB(navController = navController)
                    }
                }
            ) { paddingValues ->
                AppNavHost(navController,
                    Modifier
                        .padding(paddingValues)
                        .windowInsetsPadding(WindowInsets.systemBars))
            }
//            ) { paddingValues ->
//                AppNavHost(navController, Modifier.padding(paddingValues))
//            }
        }
    }

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val screens = listOf(Screen.Home, Screen.Wishlist, Screen.Analytics, Screen.Setting)

    val backgroundColor = colorResource(id = R.color.bottom_bar_background)
    val selectedColor = colorResource(id = R.color.tab_selected)
    val unselectedColor = colorResource(id = R.color.tab_unselected)
    val tabIndicatorColor = colorResource(id = R.color.tab_indicator)

    Column{
        // Top Divider
        HorizontalDivider(
            thickness = 1.dp, // Adjust thickness as needed
            color =  colorResource(id = R.color.border_color)
        )
        NavigationBar(
            containerColor = backgroundColor
        ) {
            screens.forEach { screen ->
                val isSelected = currentDestination == screen.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentDestination != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {

                        when (screen) {
                            is Screen.Home -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.MoneyCheck,
                                    contentDescription = "money check",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// users icon from font awesome icons
                            is Screen.Wishlist -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Heart,
                                    contentDescription = "heart",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// Services icon from font awesome icons
                            is Screen.Analytics -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.ChartBar,
                                    contentDescription = "chart",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// qr code icon from font awesome icons
                            is Screen.Setting -> {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Cog,
                                    contentDescription = "setting",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            else ->  {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.Home,
                                    contentDescription = "home",
                                    tint = if (isSelected) selectedColor else unselectedColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }// Home icon from font awesome icons  // Fallback icon
                        }

                    },
                    label = {
                        Text(
                            text = screen.route.replaceFirstChar { it.titlecase(Locale.ROOT) },
                            color = if (isSelected) selectedColor else unselectedColor // Apply custom colors
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = selectedColor,
                        unselectedIconColor = unselectedColor,
                        selectedTextColor = selectedColor,
                        unselectedTextColor = unselectedColor,
                        indicatorColor = tabIndicatorColor // Change the background color of selected tab
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(navController = rememberNavController())
}
