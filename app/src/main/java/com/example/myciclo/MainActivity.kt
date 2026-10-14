package com.example.myciclo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.myciclo.navigation.AppNavigation
import com.example.myciclo.ui.theme.MyCicloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCicloTheme {
                MyCicloApp()
            }
        }
    }
}

@Composable
fun MyCicloApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}
