package com.example.composeloginzkb

import com.example.composeloginzkb.screen.login.LoginViewModel
import org.junit.Assert.*
import org.junit.Test
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginViewModelUnitTest {
    @Test
    fun viewModelValidatesNameCorrectly() {
        val viewModel = LoginViewModel()
        viewModel.updateName("a")
        viewModel.validate()

        assertEquals("a", viewModel.name.value)
        assertTrue(viewModel.isNameValid.value)
    }

    @Test
    fun viewModelValidatesEmailCorrectly() {
        val viewModel = LoginViewModel()
        viewModel.updateEmail("a")
        viewModel.validate()

        assertEquals("a", viewModel.email.value)
        assertFalse(viewModel.isEmailValid.value)

        viewModel.updateEmail("a@a.c")
        viewModel.validate()

        assertFalse(viewModel.isEmailValid.value)

        viewModel.updateEmail("a@a.ch")
        viewModel.validate()

        assertFalse(viewModel.isEmailValid.value)
    }

    @Test
    fun viewModelValidatesBirthdayCorrectly() {
        val viewModel = LoginViewModel()
        val beforeDate = viewModel.beforeDate.plusYears(1)
        val afterDate = viewModel.afterDate.minusYears(1)
        val validDate = viewModel.beforeDate.minusYears(1)

        viewModel.updateBirthday(beforeDate)
        viewModel.validate()

        assertEquals(beforeDate.format(DateTimeFormatter.ISO_LOCAL_DATE), viewModel.birthday.value)
        assertFalse(viewModel.isBirthdayValid.value)

        viewModel.updateBirthday(afterDate)
        viewModel.validate()

        assertFalse(viewModel.isBirthdayValid.value)

        viewModel.updateBirthday(validDate)
        viewModel.validate()

        assertTrue(viewModel.isBirthdayValid.value)
    }
}