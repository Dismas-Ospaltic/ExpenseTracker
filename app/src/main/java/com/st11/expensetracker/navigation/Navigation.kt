package com.st11.expensetracker.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import androidx.compose.animation.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.st11.expensetracker.screens.AnalyticsScreen
import com.st11.expensetracker.screens.HomeScreen
import com.st11.expensetracker.screens.NotificationPreferenceScreen
import com.st11.expensetracker.screens.OnboardingScreen
import com.st11.expensetracker.screens.SelectCurrencyScreen
import com.st11.expensetracker.screens.SettingScreen
import com.st11.expensetracker.screens.WishlistScreen
import com.st11.expensetracker.screens.SplashScreen
import com.st11.expensetracker.viewmodel.CurrencyViewModel
import com.st11.expensetracker.viewmodel.OnboardingViewModel
import org.koin.androidx.compose.getViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Analytics : Screen("analytics")
    object Wishlist : Screen("Wishlist")

//    object Detail : Screen("Detail/{itemId}") {
//        fun createRoute(itemId: String) = "eventDetail/$itemId"
//    }


    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Setting : Screen("setting")
    object SelectCurrency : Screen("selectCurrency")
    object NotificationPref : Screen("notificationPref")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier) {

    val mainViewModel: OnboardingViewModel = getViewModel()
    val isOnboardingCompleted by mainViewModel.isOnboardingCompleted.collectAsState(initial = false)


    val currencyViewModel: CurrencyViewModel = getViewModel()
    val isCurrencySet by currencyViewModel.isCurrencySet.collectAsState(initial = false)

    AnimatedNavHost(
        navController,
        startDestination = Screen.Splash.route,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Analytics.route) { AnalyticsScreen(navController) }
        composable(Screen.Wishlist.route) { WishlistScreen(navController) }
        composable(Screen.SelectCurrency.route) { SelectCurrencyScreen(navController) }
        composable(Screen.Setting.route) { SettingScreen(navController) }
        composable(Screen.NotificationPref.route) { NotificationPreferenceScreen(navController) }


//        composable(Screen.Detail.route) { backStackEntry ->
//            val itemId = backStackEntry.arguments?.getString("itemId") ?: "Unknown"
//            DetailScreen(navController, itemId)
//        }

        composable(Screen.SelectCurrency.route) {
            LaunchedEffect(isCurrencySet) {
                if (isCurrencySet) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SelectCurrency.route) { inclusive = true }
                    }
                }
            }

            // Always show CreateIdentityScreen unless already created
            SelectCurrencyScreen(navController)
        }




        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigate = {
                    when {
                        isOnboardingCompleted -> navController.navigate(Screen.SelectCurrency.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }

                        else -> navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }

                })
        }



        composable(Screen.Onboarding.route) {  OnboardingScreen( onCompleteOnboarding = {
            mainViewModel.completeOnboarding()
            navController.navigate(Screen.SelectCurrency.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }

            }
        }
        )
        }

    }
}