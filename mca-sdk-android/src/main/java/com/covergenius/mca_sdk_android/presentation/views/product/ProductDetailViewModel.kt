package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.use_case.SelectFieldsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    application: Application,
    val selectFieldsUseCase: SelectFieldsUseCase
) : AndroidViewModel(application) {

    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")

    var formIndex = mutableStateOf(0)
    val product = mutableStateOf<ProductDetail?>(null)

    private var _select = mutableStateOf(mutableMapOf<String, List<String>>())

    var select: State<Map<String, List<String>>> = _select;

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

    fun getSelect(key: String, url: String) {
        Log.i("", "Got here 1")
        selectFieldsUseCase(url).onEach { result ->
            Log.i("", "Got here 2")
            when (result) {
                is Resource.Success -> {
                    Log.i("", "Got here 3")
                    result.data?.let {
                        Log.i("", "Got here 3773979333")
                        _select.value[key] = it.data
                        select = _select
                        formIndex.value = formIndex.value + 1
                        formIndex.value = formIndex.value - 1
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
            Log.i("", "Got here 4")
        }.launchIn(viewModelScope)
        Log.i("", "Got here 5")
    }


}