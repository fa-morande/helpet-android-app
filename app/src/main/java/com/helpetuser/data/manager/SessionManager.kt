package com.helpetuser.data.manager

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val PREF_NAME = "HelpetSession"
private const val KEY_USER_ID = "userId"

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Flow que emite el ID del usuario (o null si no hay nadie logueado)
    private val _userIdFlow = MutableStateFlow<Int?>(getUserId())
    val userIdFlow: StateFlow<Int?> = _userIdFlow.asStateFlow()

    private fun getUserId(): Int? {
        val id = sharedPreferences.getInt(KEY_USER_ID, -1)
        return if (id == -1) null else id
    }

    fun saveUserId(id: Int) {
        sharedPreferences.edit().putInt(KEY_USER_ID, id).apply()
        _userIdFlow.value = id
    }

    fun logout() {
        sharedPreferences.edit().remove(KEY_USER_ID).apply()
        _userIdFlow.value = null
    }
}