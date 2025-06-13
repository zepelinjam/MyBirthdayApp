package com.yurcha.mybirthdayapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yurcha.mybirthdayapp.presentation.ui.babydetails.BabyDetailsScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.DetailsScreen.route
    ) {
        composable(Screen.DetailsScreen.route) {
            BabyDetailsScreen(
                onNavigateNext = {
                    // handle later

                    /*
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        set("name", state.name)
                        set("birthdate", state.birthdate)
                        set("photoUri", state.photoUri?.toString())
                    }
                    navController.navigate(Screen.CelebrationScreen.route) */
                }
            )
        }
        composable(Screen.CelebrationScreen.route) {
/*            CelebrationScreen(
                onBack = {
                    navController.popBackStack()
                }
            ) */
        }
    }
}