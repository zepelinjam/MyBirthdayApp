package com.yurcha.mybirthdayapp.presentation.ui.navigation

sealed class Screen(val route: String) {
    object DetailsScreen : Screen("details_screen")
    object CelebrationScreen : Screen("celebration_screen")
}