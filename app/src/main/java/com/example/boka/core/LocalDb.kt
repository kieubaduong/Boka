package com.example.boka.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "boka_data_store")

object PreferencesKeys {
    val TOKEN = stringPreferencesKey("token")
}

suspend fun clearToken(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.remove(PreferencesKeys.TOKEN)
    }
}