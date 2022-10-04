package com.covergenius.mca_sdk_android.presentation.views.product_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.covergenius.mca_sdk_android.common.Constants
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.domain.model.PaymentOption
import com.covergenius.mca_sdk_android.domain.use_case.InitialiseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val initUseCase: InitialiseUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(ProductListState())
    val state: State<ProductListState> = _state

    init {
        val token = savedStateHandle.get<String>(Constants.TOKEN_QUERY)
        val paymentOption = savedStateHandle.get<String>(Constants.PAYMENT_QUERY)

        initialise(token!!,PaymentOption.Gateway)
    }

    fun initialise(token: String, paymentOption: PaymentOption) {
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
        }
    }
}