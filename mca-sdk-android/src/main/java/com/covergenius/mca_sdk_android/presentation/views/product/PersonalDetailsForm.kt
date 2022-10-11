package com.covergenius.mca_sdk_android.presentation.views.product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.common.utils.Log
import com.covergenius.mca_sdk_android.data.cache.SELECTED_PRODUCT_KEY
import com.covergenius.mca_sdk_android.data.cache.getString
import com.covergenius.mca_sdk_android.data.remote.dto.FormField
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.fromJson
import com.covergenius.mca_sdk_android.data.remote.dto.getPriorityFields
import com.covergenius.mca_sdk_android.domain.model.Product
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.presentation.views.components.DropdownField
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverButton
import com.covergenius.mca_sdk_android.presentation.views.components.MyCoverTemplate
import com.covergenius.mca_sdk_android.presentation.views.components.TitledTextField
import com.covergenius.mca_sdk_android.presentation.views.product_list.ProductListViewModel
import kotlinx.coroutines.flow.first


@Composable
fun ProductDetailsForm(
    onContinuePressed: () -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {
    val hintText by remember { mutableStateOf("Enter details as it appear on legal documents") }

    val context = LocalContext.current

    val animationTime = 200 // milliseconds
    val animationTimeExit = 0 // milliseconds

    val product = context.getString(SELECTED_PRODUCT_KEY).fromJson(ProductDetail::class.java)

    Log.d("Productinfo", "product name ${product?.name}")

    MyCoverTemplate(content = {

        Column() {
            if (product != null) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(colorPrimaryBg)
                            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "",
                            tint = colorGreen,
                            modifier = Modifier.size(12.dp)
                        )
                        Box(modifier = Modifier.width(10.dp))
                        Text(
                            text = hintText,
                            style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            "Learn more",
                            style = MaterialTheme.typography.h1.copy(
                                color = colorAccent,
                                fontSize = 10.sp,
                                fontStyle = FontStyle.Italic
                            )
                        )
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Text(
                            "Underwritten by ",
                            style = MaterialTheme.typography.h1.copy(fontSize = 12.sp, color = colorGrey)
                        )
                        Text(
                            "AIICO",
                            style = MaterialTheme.typography.h1.copy(colorSpaceGray, fontSize = 12.sp)
                        )
                        Box(Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.aiico),
                            contentDescription = "",
                            modifier = Modifier
                                .width(40.dp)
                                .height(12.dp)
                        )
                    }

                    Box(Modifier.height(8.dp))
                    AnimatedVisibility(

                        modifier = Modifier.fillMaxWidth(),
                        visible = true, //viewModel.formCursor < viewModel.formFieldList.size - 1,
                        enter = slideInHorizontally(
                            initialOffsetX = { -300 },
                            animationSpec = tween(
                                durationMillis = animationTime,
                                easing = LinearEasing
                            )
                        ),
                        exit = slideOutHorizontally(
                            targetOffsetX = { -300 },
                            animationSpec = tween(
                                durationMillis = animationTimeExit,
                                easing = LinearEasing
                            )
                        )
                    ) {
                        FormOne(product, 0/*viewModel.formCursor*/)
                    }

                    MyCoverButton("Continue", onPressed = {
                      /*  if (viewModel.formCursor != viewModel.formFieldList.size - 1) {
                            viewModel.formCursor.+2
                        } else {
                            onContinuePressed()
                        }*/
                    })
                }
            }
        }
    })
}

@Composable
fun FormOne(productDetails: ProductDetail?, formCursor: Int) {

    if (productDetails == null) return

    //gets 3 fields only
    val fields = productDetails.formFields.getPriorityFields().subList(formCursor, formCursor + 3)

    LazyColumn {
        items(fields.size) {
            val formField = fields[it]
            TitledTextField(
                placeholderText = formField.description,
                title = formField.label
            )
        }
    }
}

@Composable
fun FormTwo() {
    Column {
        Row {
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                DropdownField(
                    title = "Vehicle Type",
                    options = listOf("Commercial", "Personal")
                )
            }
            Box(Modifier.width(10.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                DropdownField(
                    title = "Kind of Vehicle",
                    options = listOf("Car", "Bus", "Trailer", "Motorcycle").sorted()
                )
            }
        }
        TitledTextField(placeholderText = "Enter Vehicle Plate Number", title = "Vehicle Plate No.")
    }
}

