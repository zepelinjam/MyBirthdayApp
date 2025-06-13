package com.yurcha.mybirthdayapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.yurcha.mybirthdayapp.presentation.ui.navigation.AppNavGraph
import com.yurcha.mybirthdayapp.presentation.ui.theme.MyBirthdayAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true) // removes fullscreen mode
        setContent {
            MyBirthdayAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    // modifier removes fullscreen mode
                    modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
                    content = { padding ->
                        Column(modifier = Modifier.padding(padding)) {
                            AppNavGraph(navController = navController)
                        }
                    }
                )
            }
        }
    }
}