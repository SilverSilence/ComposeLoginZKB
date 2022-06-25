package com.example.composeloginzkb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.composeloginzkb.screen.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.LoginScreen.route) {
        loginGraph(navController)
        homeGraph()
    }
}