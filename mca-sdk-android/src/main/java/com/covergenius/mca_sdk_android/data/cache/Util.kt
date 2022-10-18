package com.covergenius.mca_sdk_android.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

const val PREFERENCE_NAME = "MyCover_Datastore"
const val SELECTED_PRODUCT_KEY = "selected_product_key"
const val SAVED_FORM_DATA_ENTRY = "form_data_entry"
const val BUSINESS_INSTANCE_ID = "business_instance_id"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

fun Context.writeString(key: String, value: String) {
    runBlocking {
        datastore.edit { pref -> pref[stringPreferencesKey(key)] = value }
    }
}

fun Context.getString(key: String): String {
    return runBlocking {
        datastore.data.first()[stringPreferencesKey(key)] ?: ""
    }
}


fun Context.getSelectedProduct(): ProductDetail =
    getString(SELECTED_PRODUCT_KEY).fromJson(ProductDetail::class.java)