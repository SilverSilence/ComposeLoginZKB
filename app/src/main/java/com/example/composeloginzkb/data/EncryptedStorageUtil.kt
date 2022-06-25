package com.example.composeloginzkb.data

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


object EncryptedStorageUtil {
    private const val nameKey = "NAME"
    private const val emailKey = "EMAIL"
    private const val birthdayKey = "BIRTHDAY"

    fun store(context: Context, name: String, email: String, birthday: String) {
        var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        var sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val editor = sharedPreferences.edit()
        editor.putString(nameKey, name)
        editor.putString(emailKey, email)
        editor.putString(birthdayKey, birthday)
        editor.apply()
    }
}