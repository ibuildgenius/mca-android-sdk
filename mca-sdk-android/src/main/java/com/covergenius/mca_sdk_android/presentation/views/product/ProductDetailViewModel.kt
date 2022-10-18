package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.covergenius.mca_sdk_android.data.cache.SELECTED_PRODUCT_KEY
import com.covergenius.mca_sdk_android.data.cache.getString
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext //TODO("implement a better solution")

    var formIndex =  mutableStateOf(0)
    val product = mutableStateOf<ProductDetail?>(null)

    private val formData = mutableMapOf<String, Any>()

    init {
        getSelectedForm()
    }

    private fun getSelectedForm() {
         product.value = context.getString(SELECTED_PRODUCT_KEY).fromJson(ProductDetail::class.java)
    }

    fun saveFieldEntries() {
        val gson = Gson()
        gson.toJson(formData)
    }


    fun addFormDataEntry(key: String, value: String) {
        formData[key] = value
    }


}