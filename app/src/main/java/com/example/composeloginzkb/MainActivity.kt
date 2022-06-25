package com.example.composeloginzkb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeloginzkb.navigation.Navigation
import com.example.composeloginzkb.ui.theme.ComposeLoginZKBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLoginZKBTheme {
                Navigation()
            }
        }
    }

}