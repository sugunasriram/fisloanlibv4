package com.github.sugunasriram.fisloanlibv4.fiscode.utils.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

object TokenManager{
    private lateinit var dataStore: DataStore<Preferences>

    fun initialize(dataStore: DataStore<Preferences>) {
        this.dataStore = dataStore
    }

    suspend fun save(key:String,value:String){
       val  dataStoreKey = stringPreferencesKey(key)
        dataStore.edit {settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun read(key:String):String?{
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }
    fun isInitialized(): Boolean {
        return this::dataStore.isInitialized
    }
}
