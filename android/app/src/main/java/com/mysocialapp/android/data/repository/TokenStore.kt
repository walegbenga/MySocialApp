package com.mysocialapp.android.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

interface AuthTokenStorage { fun token(): String?; fun save(token: String); fun clear() }

class TokenStore(context: Context) : AuthTokenStorage {
    private val preferences = EncryptedSharedPreferences.create(context, "secure_auth", MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(), EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    override fun token(): String? = preferences.getString("token", null)
    override fun save(token: String) { preferences.edit().putString("token", token).apply() }
    override fun clear() { preferences.edit().remove("token").apply() }
}
