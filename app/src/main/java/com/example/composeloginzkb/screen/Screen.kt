package com.example.composeloginzkb.screen

import androidx.annotation.StringRes
import com.example.composeloginzkb.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object LoginScreen : Screen("login", R.string.screen_login)
    object HomeScreen : Screen("home", R.string.screen_home)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
