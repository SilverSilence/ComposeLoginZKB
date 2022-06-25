package com.example.composeloginzkb.screen.home

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeloginzkb.R
import com.example.composeloginzkb.screen.home.HomeUI.BIRTHDAY_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.EMAIL_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.NAME_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.TEST_TAG

object HomeUI {
    @VisibleForTesting
    const val TEST_TAG = "HomeUI"

    @VisibleForTesting
    const val NAME_TEST_TAG = TEST_TAG + "_name"

    @VisibleForTesting
    const val EMAIL_TEST_TAG = TEST_TAG + "_email"

    @VisibleForTesting
    const val BIRTHDAY_TEST_TAG = TEST_TAG + "_birthday"
}

@Composable
fun HomeUI(name: String, email: String, birthday: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(TEST_TAG),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.testTag(NAME_TEST_TAG),
            text = stringResource(id = R.string.home_screen_registration_thanks, name)
        )
        Text(
            text = stringResource(id = R.string.home_screen_email, email),
            modifier = Modifier.testTag(EMAIL_TEST_TAG)
        )
        Text(
            modifier = Modifier.testTag(BIRTHDAY_TEST_TAG),
            text = stringResource(id = R.string.home_screen_birthday, birthday)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeUI() {
    HomeUI("name", "email", "2000-10-10")
}