package com.covergenius.mca_sdk_android.presentation.views.product_list

import android.app.Application
import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.common.Constants
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.data.cache.SELECTED_PRODUCT_KEY
import com.covergenius.mca_sdk_android.data.cache.getString
import com.covergenius.mca_sdk_android.data.cache.writeString
import com.covergenius.mca_sdk_android.data.remote.dto.FormField
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import com.covergenius.mca_sdk_android.data.remote.dto.toJson
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.use_case.InitialiseUseCase
import com.covergenius.mca_sdk_android.domain.use_case.SCOPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val appContext: Application,
    private val initUseCase: InitialiseUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(ProductListState())
    private val _sP = mutableStateOf<ProductDetail?>(null)

    var formFieldList: Flow<List<FormField>> = MutableStateFlow(listOf())
    var formCursor: Flow<Int> = MutableStateFlow(0)

    val state: State<ProductListState> = _state
    val selectedProduct: State<ProductDetail?> = _sP

    var product: ProductDetail? = null

    init {
        //TODO("Secure token")
        initialise("MCAPUBK_TEST|48c01008-5f01-4705-b63f-e71ef5fc974f",PaymentOption.Gateway)

        if (state.value.response != null) {
            formFieldList = MutableStateFlow(state.value.response?.data?.productDetails?.get(0)?.formFields ?: listOf())
        }
    }

    private fun initialise(token: String, paymentOption: PaymentOption) {
        initUseCase(token, paymentOption).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = ProductListState(error = result.message ?: "An error occurred")
                }
                is Resource.Loading -> {
                    _state.value = ProductListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ProductListState(response = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}