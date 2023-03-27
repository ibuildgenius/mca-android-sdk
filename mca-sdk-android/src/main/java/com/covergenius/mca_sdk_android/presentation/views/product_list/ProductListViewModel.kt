package com.covergenius.mca_sdk_android.presentation.views.product_list

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.covergenius.mca_sdk_android.Credentials
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.cache.*
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
    application: Application
) : AndroidViewModel(application) {
    private val _state = mutableStateOf(ProductListState())
    private val _sP = mutableStateOf<ProductDetail?>(null)

    val state: State<ProductListState> = _state
    val selectedProduct: State<ProductDetail?> = _sP

    var product: ProductDetail? = null

    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")


    init {
        //TODO("Secure token")
        initialise(Credentials.token, PaymentOption.Gateway)
    }

    fun initialise(token: String, paymentOption: PaymentOption = PaymentOption.Gateway) {
        //rest entries
        context.clearCache()

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