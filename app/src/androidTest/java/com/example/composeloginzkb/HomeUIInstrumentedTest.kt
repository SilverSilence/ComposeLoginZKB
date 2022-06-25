package com.example.composeloginzkb

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.composeloginzkb.screen.home.HomeUI
import com.example.composeloginzkb.screen.home.HomeUI.BIRTHDAY_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.EMAIL_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.NAME_TEST_TAG
import com.example.composeloginzkb.screen.home.HomeUI.TEST_TAG
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeUIInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeUIDisplaysCorrectly() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val name = "name"
        val email = "email"
        val birthday = "2020-20-02"
        composeTestRule.setContent {
            HomeUI(name, email, birthday)
        }

        assertFieldsAreDisplayed()

        composeTestRule.onNode(hasTestTag(NAME_TEST_TAG), true).assertTextEquals(
            context.getString(R.string.home_screen_registration_thanks, name)
        )
        composeTestRule.onNode(hasTestTag(EMAIL_TEST_TAG), true).assertTextEquals(
            context.getString(R.string.home_screen_email, email)
        )
        composeTestRule.onNode(hasTestTag(BIRTHDAY_TEST_TAG), true).assertTextEquals(
            context.getString(R.string.home_screen_birthday, birthday)
        )
    }

    private fun assertFieldsAreDisplayed() {
        composeTestRule.onNode(hasTestTag(TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(NAME_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(EMAIL_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(BIRTHDAY_TEST_TAG)).assertIsDisplayed()
    }
}