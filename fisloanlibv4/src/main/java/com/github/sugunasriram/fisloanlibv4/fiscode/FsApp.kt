package com.github.sugunasriram.fisloanlibv4.fiscode

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage.TokenManager

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FsApp : Application() {
    var token: String? = null

    companion object {
        private lateinit var instance: FsApp
        fun getInstance(): FsApp {
            return if (this::instance.isInitialized) {
                instance
            } else {
                instance = FsApp()
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        TokenManager.initialize(dataStore)
    }
}
