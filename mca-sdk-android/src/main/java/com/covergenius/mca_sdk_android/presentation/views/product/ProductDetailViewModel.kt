package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext //TODO("implement a better solution")

    var formIndex =  mutableStateOf(0)
    val product = mutableStateOf<ProductDetail?>(null)

    private val formData = mutableMapOf<String, String>()

    init {
        product.value = context.getSelectedProduct()
    }


    fun saveFieldEntries() {
        val gson = Gson().toJson(formData)
        context.writeString(SAVED_FORM_DATA_ENTRY, gson)
    }


    fun addFormDataEntry(key: String, value: String) {
        formData[key] = value
    }

    fun getFormEntry(key: String): String? {
        return formData[key]
    }


}