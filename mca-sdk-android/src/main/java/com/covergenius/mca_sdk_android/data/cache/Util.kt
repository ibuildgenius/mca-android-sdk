package com.covergenius.mca_sdk_android.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PREFERENCE_NAME = "MyCover_Datastore"
const val SELECTED_PRODUCT_KEY = "selected_product_key"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

suspend fun Context.writeString(key: String, value: String) {
    datastore.edit { pref -> pref[stringPreferencesKey(key)] = value }
}

fun Context.getString(key: String): Flow<String> {
    return datastore.data.map { pref ->
        pref[stringPreferencesKey(key)] ?: ""
    }
}