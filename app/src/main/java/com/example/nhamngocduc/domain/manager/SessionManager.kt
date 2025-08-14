package com.example.nhamngocduc.domain.manager

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SessionManager(
    private val dataStore: DataStore<Preferences>
) {
    val currentUsername: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("Session manager", "ERROR reading preferences")
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }

    suspend fun login(username: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(USERNAME_KEY)
        }
    }

    private companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
    }
}
