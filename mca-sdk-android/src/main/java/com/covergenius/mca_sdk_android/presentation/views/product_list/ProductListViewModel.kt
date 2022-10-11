package com.covergenius.mca_sdk_android.presentation.views.product_list

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.use_case.InitialiseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val initUseCase: InitialiseUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(ProductListState())
    private val _sP = mutableStateOf<ProductDetail?>(null)

    val state: State<ProductListState> = _state
    val selectedProduct: State<ProductDetail?> = _sP

    var product: ProductDetail? = null

    init {
        //TODO("Secure token")
        initialise("MCAPUBK_TEST|48c01008-5f01-4705-b63f-e71ef5fc974f",PaymentOption.Gateway)

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