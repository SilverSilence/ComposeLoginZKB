package com.example.composeloginzkb.screen.login

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeloginzkb.R
import com.example.composeloginzkb.screen.login.LoginUI.BIRTHDAY_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.BIRTHDAY_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.EMAIL_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.EMAIL_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.NAME_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.NAME_WARNING_TEST_TAG
import com.example.composeloginzkb.screen.login.LoginUI.TEST_TAG
import com.example.composeloginzkb.ui.datepicker.DatePickerUtil
import java.time.LocalDateTime

object LoginUI {
    @VisibleForTesting
    const val TEST_TAG = "LoginUI"

    @VisibleForTesting
    const val NAME_TEST_TAG = TEST_TAG + "_name"

    @VisibleForTesting
    const val NAME_WARNING_TEST_TAG = NAME_TEST_TAG + "_warning"

    @VisibleForTesting
    const val EMAIL_TEST_TAG = TEST_TAG + "_email"

    @VisibleForTesting
    const val EMAIL_WARNING_TEST_TAG = EMAIL_TEST_TAG + "_warning"

    @VisibleForTesting
    const val BIRTHDAY_TEST_TAG = TEST_TAG + "_birthday"

    @VisibleForTesting
    const val BIRTHDAY_WARNING_TEST_TAG = BIRTHDAY_TEST_TAG + "_warning"
}

@Composable
fun LoginUI(
    navigateToHome: (username: String, email: String, birthday: String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val birthday by viewModel.birthday.collectAsState()
    val isNameValid by viewModel.isNameValid.collectAsState()
    val isEmailValid by viewModel.isEmailValid.collectAsState()
    val isBirthdayValid by viewModel.isBirthdayValid.collectAsState()

    val spacerHeight = 8.dp

    Column(
        modifier = Modifier
            .testTag(TEST_TAG)
            .padding(50.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NameFieldUI(name, viewModel::updateName, isNameValid)
        Spacer(Modifier.height(spacerHeight))
        EmailFieldUI(email, viewModel::updateEmail, isEmailValid)
        Spacer(Modifier.height(spacerHeight))

        val context = LocalContext.current
        BirthdayFieldUI(birthday, context, viewModel::updateBirthday, isBirthdayValid)
        Spacer(Modifier.height(spacerHeight))

        RegisterButtonUI(viewModel, navigateToHome, name, email, birthday)
    }
}

@Composable
private fun RegisterButtonUI(
    viewModel: LoginViewModel,
    navigateToHome: (username: String, email: String, birthday: String) -> Unit,
    name: String,
    email: String,
    birthday: String
) {
    val context = LocalContext.current
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (viewModel.validate()) {
                if (viewModel.register(context)) {
                    navigateToHome(name, email, birthday)
                } else {
                    //TODO display login failure message
                }
            }
        }
    ) { Text(text = stringResource(R.string.screen_login_register)) }
}

@Composable
private fun BirthdayFieldUI(
    birthday: String,
    context: Context,
    updateBirthday: (LocalDateTime) -> Unit,
    isBirthdayValid: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextField(
            value = stringResource(id = R.string.screen_login_birthday_button, birthday),
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .weight(0.8f)
                .testTag(BIRTHDAY_TEST_TAG)
        )
        Button(
            onClick = { DatePickerUtil.openDatePicker(context, updateBirthday) },
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
        ) {
            Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "Date")
        }
    }
    if (!isBirthdayValid) {
        Text(
            modifier = Modifier.testTag(BIRTHDAY_WARNING_TEST_TAG),
            text = stringResource(id = R.string.screen_login_invalid_birthday),
            color = Color.Red
        )
    }
}

@Composable
private fun EmailFieldUI(
    email: String,
    updateEmail: (String) -> Unit,
    isEmailValid: Boolean
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(EMAIL_TEST_TAG),
        value = email,
        onValueChange = updateEmail,
        label = { Text(stringResource(id = R.string.screen_login_email_label)) },
        placeholder = { Text(stringResource(id = R.string.screen_login_email_placeholder)) },
        trailingIcon = { WarningIconUI(isEmailValid) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { }),
        singleLine = true
    )
    if (!isEmailValid) {
        Text(
            modifier = Modifier.testTag(EMAIL_WARNING_TEST_TAG),
            text = stringResource(id = R.string.screen_login_invalid_email),
            color = Color.Red
        )
    }
}

@Composable
private fun NameFieldUI(
    name: String,
    updateName: (String) -> Unit,
    isNameValid: Boolean
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(NAME_TEST_TAG),
        value = name,
        onValueChange = updateName,
        label = { Text(stringResource(id = R.string.screen_login_username_label)) },
        placeholder = { Text(stringResource(id = R.string.screen_login_username_placeholder)) },
        trailingIcon = { WarningIconUI(isNameValid) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
        singleLine = true
    )
    if (!isNameValid) {
        Text(
            modifier = Modifier.testTag(NAME_WARNING_TEST_TAG),
            text = stringResource(id = R.string.screen_login_invalid_name),
            color = Color.Red
        )
    }
}

@Composable
private fun WarningIconUI(isValid: Boolean) {
    if (!isValid) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Warning",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoErrorPreview() {
    val viewModel = LoginViewModel()
    viewModel.updateName("Name")
    viewModel.updateEmail("mail@example.com")
    viewModel.updateBirthday(LocalDateTime.now())
    LoginUI(navigateToHome = { _, _, _ -> }, viewModel)
}

@Preview(showBackground = true)
@Composable
fun AllErrorPreview() {
    val viewModel = LoginViewModel()
    viewModel.updateName("")
    viewModel.updateEmail("mail.com")
    viewModel.updateBirthday(LocalDateTime.now().plusDays(1))
    viewModel.validate()
    LoginUI(navigateToHome = { _, _, _ -> }, viewModel)
}