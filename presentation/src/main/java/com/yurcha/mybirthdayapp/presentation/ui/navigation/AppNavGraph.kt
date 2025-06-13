package com.yurcha.mybirthdayapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yurcha.mybirthdayapp.presentation.ui.babydetails.BabyDetailsScreen
import com.yurcha.mybirthdayapp.presentation.ui.celebration.CelebrationScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DetailsScreen.route
    ) {
        composable(route = Screen.DetailsScreen.route) {
            BabyDetailsScreen(
                onNavigateNext = {
                    navController.navigate(Screen.CelebrationScreen.route)
                }
            )
        }

        composable(route = Screen.CelebrationScreen.route) {
            CelebrationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}