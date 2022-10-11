package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.covergenius.mca_sdk_android.data.cache.SELECTED_PRODUCT_KEY
import com.covergenius.mca_sdk_android.data.cache.getString
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext //TODO("implement a better solution")

    var formIndex =  mutableStateOf(0)
    val product = mutableStateOf<ProductDetail?>(null)

    init {
        getSelectedForm()
    }

    private fun getSelectedForm() {
         product.value = context.getString(SELECTED_PRODUCT_KEY).fromJson(ProductDetail::class.java)
    }


}