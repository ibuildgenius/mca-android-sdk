package com.covergenius.mca_sdk_android.presentation.views.product_list

import com.covergenius.mca_sdk_android.data.remote.dto.Response

data class ProductListState(
    val isLoading: Boolean = false,
    val response: Response? = null,
    val error: String = ""
)