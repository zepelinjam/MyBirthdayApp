package com.yurcha.mybirthdayapp.presentation.ui.navigation

sealed class Screen(val route: String) {
    object DetailsScreen : Screen("details")
    object CelebrationScreen : Screen("celebration")
}