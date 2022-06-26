package com.example.composeloginzkb.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeloginzkb.screen.Screen
import com.example.composeloginzkb.ui.home.HomeUI
import com.example.composeloginzkb.ui.login.LoginUI

fun NavGraphBuilder.loginGraph(navController: NavController) {
    composable(Screen.LoginScreen.route) {
        LoginUI(navigateToHome = { name, email, birthday ->
            navController.navigate(
                Screen.HomeScreen.withArgs(
                    name,
                    email,
                    birthday
                )
            )
        })
    }
}

fun NavGraphBuilder.homeGraph() {
    val name = "name"
    val email = "email"
    val birthday = "birthday"

    composable(
        route = Screen.HomeScreen.route + "/{$name}/{$email}/{$birthday}",
        arguments = listOf(
            navArgument(name) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(email) {
                type = NavType.StringType
                nullable = false
            },
            navArgument(birthday) {
                type = NavType.StringType
                nullable = false
            }
        )
    ) { entry ->
        val arguments = entry.arguments!!
        HomeUI(
            name = arguments.getString(name)!!,
            email = arguments.getString(email)!!,
            birthday = arguments.getString(birthday)!!
        )
    }
}