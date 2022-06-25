package com.example.composeloginzkb

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.composeloginzkb.screen.login.LoginUI
import com.example.composeloginzkb.screen.login.LoginUI.BIRTHDAY_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.BIRTHDAY_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.EMAIL_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.EMAIL_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.NAME_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.NAME_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.TEST_TAG
import com.example.composeloginzkb.screen.login.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginUIInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginUIDisplaysCorrectly() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val viewModel = LoginViewModel()
        composeTestRule.setContent {
            LoginUI(navigateToHome = { _, _, _ -> }, viewModel)
        }

        assertFieldsAreDisplayed()
        assertWarningsDoNotExist()
        viewModel.validate() //Trigger warnings
        assertWarningsAreDisplayed()

        viewModel.updateName("a")
        viewModel.updateEmail("a@a.a")
        viewModel.updateBirthday(viewModel.afterDate.plusYears(1))
        viewModel.validate()

        composeTestRule.onNode(hasTestTag(NAME_TEST_TAG), true)
            .assertTextEquals(viewModel.name.value)

        composeTestRule.onNode(hasTestTag(EMAIL_TEST_TAG), true)
            .assertTextEquals(viewModel.email.value)

        composeTestRule.onNode(hasTestTag(BIRTHDAY_TEST_TAG), true).assertTextEquals(
            context.getString(R.string.screen_login_birthday_button, viewModel.birthday.value)
        )
        assertWarningsDoNotExist()
    }

    private fun assertWarningsAreDisplayed() {
        composeTestRule.onNode(hasTestTag(NAME_WARNING_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(EMAIL_WARNING_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(BIRTHDAY_WARNING_TEST_TAG)).assertIsDisplayed()
    }

    private fun assertWarningsDoNotExist() {
        composeTestRule.onNode(hasTestTag(NAME_WARNING_TEST_TAG)).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag(EMAIL_WARNING_TEST_TAG)).assertDoesNotExist()
        composeTestRule.onNode(hasTestTag(BIRTHDAY_WARNING_TEST_TAG)).assertDoesNotExist()
    }

    private fun assertFieldsAreDisplayed() {
        composeTestRule.onNode(hasTestTag(TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(NAME_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(EMAIL_TEST_TAG)).assertIsDisplayed()
        composeTestRule.onNode(hasTestTag(BIRTHDAY_TEST_TAG)).assertIsDisplayed()
    }
}