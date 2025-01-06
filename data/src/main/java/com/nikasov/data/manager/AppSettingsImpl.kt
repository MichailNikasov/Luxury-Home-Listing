package com.nikasov.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.nikasov.domain.manager.AppSettings
import javax.inject.Inject
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppSettingsImpl @Inject constructor(private val context: Context): AppSettings {

    companion object {
        private const val SETTINGS_DATASTORE_NAME = "settings_datastore"
        private val SHOW_ERROR_KEY = booleanPreferencesKey("show_error")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATASTORE_NAME)

    override suspend fun toggleShowError() {
        context.dataStore.edit { settings -> settings[SHOW_ERROR_KEY] = !isShowError.first() }
    }

    override val isShowError: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[SHOW_ERROR_KEY] ?: false
        }
}