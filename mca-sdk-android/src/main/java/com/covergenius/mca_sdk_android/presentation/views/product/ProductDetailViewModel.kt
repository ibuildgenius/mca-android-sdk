package com.covergenius.mca_sdk_android.presentation.views.product

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.covergenius.mca_sdk_android.MCA_API_KEY
import com.covergenius.mca_sdk_android.common.Resource
import com.covergenius.mca_sdk_android.data.cache.*
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.domain.use_case.CompletePurchaseUseCase
import com.covergenius.mca_sdk_android.domain.use_case.FileUploadUseCase
import com.covergenius.mca_sdk_android.domain.use_case.SelectFieldsUseCase
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    application: Application,
    private val selectFieldsUseCase: SelectFieldsUseCase,
    private val fileUploadUSeCase: FileUploadUseCase,
    private val completePurchaseUseCase: CompletePurchaseUseCase
) : AndroidViewModel(application) {


    private val _showCancelDialog = MutableStateFlow(false)

    val showCancelDialog: StateFlow<Boolean> = _showCancelDialog.asStateFlow()

    val purchaseComplete = mutableStateOf(false)

    var showDialog = mutableStateOf(false)


    fun onOpenDialogClicked() {
        _showCancelDialog.value = true
    }

    fun onDialogConfirm() {
        _showCancelDialog.value = false
        //continue execution
    }

    fun onDialogDismiss() {
        _showCancelDialog.value = false
    }

    private val context =
        getApplication<Application>().applicationContext //TODO("implement a better solution")

    var formIndex = mutableStateOf(0)
    val product = mutableStateOf<ProductDetail?>(null)

    private var _select = mutableStateOf(mutableMapOf<String, List<String>>())

    var select: State<Map<String, List<String>>> = _select;

    private val formData = mutableMapOf<String, Any>()
    private val fileList = mutableMapOf<String, Uri>()

    init {
        product.value = context.getSelectedProduct()
    }

    fun fileUploadList() = fileList
    fun saveFieldEntries() {


        Log.i("", "Saving... $formData")

        var formDataEntry = Gson().toJson(formData)

        val existingFields = context.getString(SAVED_FORM_DATA_ENTRY)

        if (existingFields.isNotEmpty() && existingFields.endsWith("}")) {
            formDataEntry = existingFields.dropLast(1).plus(",${formDataEntry.drop(1)}")
        }

        context.writeString(SAVED_FORM_DATA_ENTRY, formDataEntry)

        Log.i("", "Saving... saved ${context.getString(SAVED_FORM_DATA_ENTRY)}")
    }

    fun addFormDataEntry(key: String, value: Any) {
        formData[key] = value
    }

    fun addToFileUploadList(key: String, value: Uri) {
        fileList[key] = value
    }

    fun getFileUploadEntry(key: String): Uri? = fileList[key]


    fun getFormEntry(key: String): String? {
        return formData[key] as String?
    }

    fun completePurchase(navigator: NavController) {
        showDialog.value = true

        if (fileList.keys.isNotEmpty()) {

            for (file in fileList) {
                val result = fileUploadUSeCase(file = file.value, token = MCA_API_KEY, contentResolver = context.contentResolver)

                if (!result.isNullOrEmpty()) {
                    addFormDataEntry(file.key, result)
                    saveFieldEntries()
                } else {
                    showDialog.value = false
                    return
                }
            }
        }

        val data = JSONObject()
        val payload = JSONObject(context.getString(SAVED_FORM_DATA_ENTRY))

        data.put("reference", context.getString(TRANSACTION_REF_KEY))
        data.put("payload", payload)

        completePurchaseUseCase(token = MCA_API_KEY, payload = data.toString()).onEach { result ->

            when (result) {
                is Resource.Loading -> {
                    showDialog.value = true
                }

                is Resource.Error -> {
                    showDialog.value = false
                }

                is Resource.Success -> {
                    showDialog.value = false
                    purchaseComplete.value = true
                    navigator.navigate(Routes.PaymentResult) {
                        popUpTo(Routes.PaymentResult) {
                            inclusive = true
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getSelect(key: String, url: String) {
        Log.i("", "Got here 1")
        selectFieldsUseCase(url).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {

                        val newList = mutableListOf<String>()

                        it.data.forEach { option ->
                            when (option) {
                                is Map<*,*> -> {

                                    newList.add((option as Map<String, Any>)["name"] as String)
                                }

                                else -> {
                                    newList.add(option.toString())
                                }


                            }
                        }

                        _select.value[key] = newList

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