package com.example.composeloginzkb.screen.login

import android.content.Context
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.example.composeloginzkb.data.EncryptedStorageUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginViewModel : ViewModel() {

    val name: StateFlow<String>
        get() = _name
    val email: StateFlow<String>
        get() = _email
    val birthday: StateFlow<String>
        get() = _birthday
    val isNameValid: StateFlow<Boolean>
        get() = _isNameValid
    val isEmailValid: StateFlow<Boolean>
        get() = _isEmailValid
    val isBirthdayValid: StateFlow<Boolean>
        get() = _isBirthdayValid

    private val _name = MutableStateFlow("")
    private val _email = MutableStateFlow("")
    private val _birthday = MutableStateFlow("")
    private val _isNameValid = MutableStateFlow(true) //Don't show warning from the start
    private val _isEmailValid = MutableStateFlow(true) //Don't show warning from the start
    private val _isBirthdayValid = MutableStateFlow(true) //Don't show warning from the start
    private var birthDate: LocalDateTime? = null

    @VisibleForTesting
    val afterDate: LocalDateTime = LocalDateTime.of(1900, 1, 1, 0, 0, 0, 0)

    @VisibleForTesting
    val beforeDate: LocalDateTime = LocalDateTime.of(2021, 12, 31, 0, 0, 0)

    fun updateName(username: String) {
        _name.value = username
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateBirthday(date: LocalDateTime) {
        birthDate = date
        _birthday.value = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun validate(): Boolean {
        updateValidation()
        return isNameValid.value && isEmailValid.value && isBirthdayValid.value
    }

    fun register(context: Context): Boolean {
        //TODO
        //val success = some backend call for registration
        //
        val success = true
        EncryptedStorageUtil.store(
            context,
            name.value,
            email.value,
            birthday.value
        )

        return success
    }

    private fun updateValidation() {
        _isNameValid.value = isNameValid()
        _isEmailValid.value = isEmailValid()
        _isBirthdayValid.value = isBirthdayValid()
    }

    private fun isNameValid(): Boolean {
        return name.value.isNotEmpty()
    }

    private fun isEmailValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    private fun isBirthdayValid(): Boolean {
        return if (birthDate == null) {
            false
        } else {
            val date = birthDate!!
            date.isBefore(beforeDate) && date.isAfter(afterDate)
        }
    }
}