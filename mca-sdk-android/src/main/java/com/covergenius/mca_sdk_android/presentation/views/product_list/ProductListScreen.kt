package com.covergenius.mca_sdk_android.presentation.views.product_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.data.cache.BUSINESS_INSTANCE_ID
import com.covergenius.mca_sdk_android.data.cache.SELECTED_PRODUCT_KEY

import com.covergenius.mca_sdk_android.data.cache.writeString
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.data.remote.dto.toJson
import com.covergenius.mca_sdk_android.presentation.theme.*
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.covergenius.mca_sdk_android.presentation.views.product_list.components.ChipGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current

    val state = viewModel.state.value

    var filterList: List<String>

    var search by remember { mutableStateOf(TextFieldValue("")) }

    var currentFilter by remember { mutableStateOf("All") }

    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        } else if (state.error.isNotBlank()) {
            Text(
                state.error,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.response != null) {

            val products = state.response.data.productDetails

            val filteredProducts =
                if (currentFilter.lowercase() != "all") products.filter { it.prefix == currentFilter } else products

            val fil = mutableListOf<String>()

            products.forEach { fil.add(it.prefix) }

            fil.add("All")

            filterList = fil.distinct().sortedBy { it }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        colorBackground
                    )
            ) {
                Text(
                    text = "Products Page",
                    style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(top = 12.dp, bottom = 25.dp)
                )
                OutlinedTextField(
                    value = search,
                    shape = textFieldShape,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "",
                            tint = colorGrey
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search Products",
                            style = MaterialTheme.typography.h3.copy(color = colorGrey)
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorPrimary,
                        unfocusedBorderColor = colorGrey
                    ),
                    onValueChange = { text: TextFieldValue -> search = text },
                    modifier = Modifier.fillMaxWidth()
                )

                ChipGroup(items = filterList, selectedString = currentFilter, onItemClick = {
                    currentFilter = it
                })

                LazyColumn {
                    items(filteredProducts.size) { index ->
                        ProductItem(product = filteredProducts[index], onItemClicked = {
                            context.writeString(
                                SELECTED_PRODUCT_KEY,
                                filteredProducts[index].toJson()
                            )

                            viewModel.state.value.response?.let {
                                context.writeString(BUSINESS_INSTANCE_ID, it.data.businessDetails.toJson())
                            }

                            navController.navigate(Routes.ProductInfo)
                        })
                    }
                }
            }
        } else {
            Text(
                "This is probably a desert",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

data class Filter(val name: String, val selected: Boolean = false)

@Composable
fun ProductItem(product: ProductDetail, onItemClicked: (value: Boolean) -> Unit) {

    Card(
        modifier = Modifier
            .background(
                colorPrimaryLight, shape = RoundedCornerShape(8.dp)
            )
            .toggleable(value = true, onValueChange = onItemClicked),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp)
        ) {
            Row() {

                Icon(
                    Icons.Default.Home, contentDescription = "", tint = colorPrimary,
                    modifier =
                    Modifier
                        .background(colorPrimaryLight.copy(alpha = 0.1f), shape = CircleShape)
                        .padding(8.dp)
                        .size(18.dp)
                )


                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 15.dp)
                ) {
                    Text(text = product.name, style = MaterialTheme.typography.h3)
                    Row(Modifier.padding(top = 10.dp)) {
                        Text(
                            text = product.prefix,
                            style = MaterialTheme.typography.h3.copy(
                                fontSize = 11.sp,
                                color = colorSpaceGray
                            )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Image(
                            painter = painterResource(id = R.drawable.leadway),
                            contentDescription = "",
                            modifier = Modifier
                                .width(14.dp)
                                .height(14.dp)
                        )
                    }
                }
                Text(text = "N${product.price}", style = MaterialTheme.typography.body2)
            }
        }
    }
}
