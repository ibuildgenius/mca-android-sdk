package com.covergenius.mca_sdk_android.presentation.views.product_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.data.remote.dto.ProductDetail
import com.covergenius.mca_sdk_android.presentation.theme.*

@Composable
fun ProductListScreen(
    onItemClicked: (value: Boolean) -> Unit,
    viewModel: ProductListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val filterList =
        mutableListOf(Filter("All", false), Filter("AIICO", false), Filter("Leadway", true))
    var search by remember { mutableStateOf(TextFieldValue("")) }

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

                ChipGroup(items = filterList)

                LazyColumn() {
                    items(products.size) { index ->
                        ProductItem(product = products[index], onItemClicked)
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

@Composable
fun Chip(name: String, isSelected: Boolean, onSelectionChange: (String) -> Unit = {}) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .border(
                BorderStroke(1.dp, if (isSelected) colorPrimary else colorGrey),
                RoundedCornerShape(50.dp)
            ),
        elevation = 0.dp,
        shape = RoundedCornerShape(50.dp),
        color = if (isSelected) colorPrimary else colorWhite
    ) {
        Row(
            modifier = Modifier.toggleable(
                value = isSelected,
                onValueChange = { onSelectionChange(name) }
            )) {
            Text(
                text = name,
                style = MaterialTheme.typography.h3,
                color = if (isSelected) colorWhite else colorSpaceGray,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ChipGroup(items: List<Filter>) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(items.size) {
                val filter = items[it]
                Chip(name = filter.name, isSelected = filter.selected, onSelectionChange = {

                })
            }
        }
    }
}