package com.covergenius.mca_sdk_android.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

const val PREFERENCE_NAME = "MyCover_Datastore"
const val SELECTED_PRODUCT_KEY = "selected_product_key"
const val SAVED_FORM_DATA_ENTRY = "form_data_entry"
const val SAVED_FILE_DATA_ENTRY = "file_upload_list"
const val BUSINESS_INSTANCE_ID = "business_instance_id"
const val PAYMENT_SUCCESS_KEY = "payment_success"
const val TRANSACTION_REF_KEY = "transaction_reference"

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

fun Context.writeString(key: String, value: String) {
    runBlocking {
        datastore.edit { pref -> pref[stringPreferencesKey(key)] = value }
    }
}

fun Context.writeBoolean(key: String, value: Boolean) {
    runBlocking {
        datastore.edit { pref -> pref[booleanPreferencesKey(key)] = value }
    }
}

fun Context.getBoolean(key: String): Boolean {
    return runBlocking {
        datastore.data.first()[booleanPreferencesKey(key)] ?: false
    }
}

fun Context.getString(key: String): String {
    return runBlocking {
        datastore.data.first()[stringPreferencesKey(key)] ?: ""
    }
}

fun Context.getSelectedProduct(): ProductDetail =
    getString(SELECTED_PRODUCT_KEY).fromJson(ProductDetail::class.java)

fun getFieldFromJson(key: String, json: String): String {
    return try {
        val entry = Gson().fromJson(json, JsonObject::class.java)
        entry.get(key).asString
    } catch (e: Exception) {
        ""
    }
}

fun Context.clearCache() {
    runBlocking {
        datastore.edit {
            it.clear()
        }
    }
}