package com.covergenius.mca_sdk_android.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.data.model.Product
import com.covergenius.mca_sdk_android.ui.theme.appBarTitleText
import com.covergenius.mca_sdk_android.ui.theme.colorPrimary
import com.covergenius.mca_sdk_android.ui.theme.colorPrimaryLight
import com.covergenius.mca_sdk_android.ui.theme.textFieldShape

@Composable
fun ProductsPage() {

    val productList = mutableListOf<Product>(Product("Gadget", "", "AIICO", "6000"))
    var search by remember { mutableStateOf(TextFieldValue("")) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Products Page",
            style = appBarTitleText,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        OutlinedTextField(
            value = search,
            shape = textFieldShape,
            placeholder = { Text(text = "Search Products") },
            onValueChange = { text: TextFieldValue -> search = text })
        LazyColumn(Modifier.padding(vertical = 15.dp)) {
            items(productList.size) { index ->
                ProductItem(product = productList[index])
            }
        }
    }
}


@Composable
fun ProductItem(product: Product) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row() {
            IconButton(
                onClick = { }, modifier =
                Modifier
                    .background(colorPrimaryLight.copy(alpha = 0.5f))
                    .padding(12.dp)
                    .clip(CircleShape)
                    .size(24.dp)
            ) {
                Icon(
                    Icons.Default.Home, contentDescription = "", tint = colorPrimary,
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 15.dp)
            ) {
                Text(text = product.name)
                Row(Modifier.padding(top = 10.dp)) {
                    Text(text = product.company, style = MaterialTheme.typography.h3)
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.leadway),
                        contentDescription = "",
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                    )
                }
            }
            Text(text = product.price)
        }
    }
}