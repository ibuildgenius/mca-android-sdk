package com.covergenius.mca_sdk_android.views.auto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.covergenius.mca_sdk_android.R
import com.covergenius.mca_sdk_android.data.model.Product
import com.covergenius.mca_sdk_android.ui.theme.*
import com.covergenius.mca_sdk_android.utils.Separator
import com.covergenius.mca_sdk_android.views.composables.DropdownField
import com.covergenius.mca_sdk_android.views.composables.MyCoverButton
import com.covergenius.mca_sdk_android.views.composables.MyCoverTemplate
import com.covergenius.mca_sdk_android.views.composables.TitledTextField


@Composable
fun AutoPersonalDetailsForm(product: Product) {
    MyCoverTemplate(content = {
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
                    text = "Enter details as it appear on legal documents",
                    style = MaterialTheme.typography.body1.copy(fontSize = 12.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
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
                        .weight(1f))
                Text("Underwritten by ", style = MaterialTheme.typography.h1.copy(fontSize = 12.sp, color = colorGrey))
                Text("AIICO", style = MaterialTheme.typography.h1.copy(colorNavyBlue, fontSize = 12.sp))
                Box(Modifier.width(4.dp))
                Image(
                    painter = painterResource(id = R.drawable.aiico),
                    contentDescription = "",
                    modifier = Modifier
                        .width(40.dp)
                        .height(12.dp)
                )
            }

            Column {
                TitledTextField(placeholderText = "First Name, Last Name", title = "Name of Plan Owner")
                TitledTextField(placeholderText = "Enter email address", title = "Email")
                TitledTextField(placeholderText = "Enter phone number", title = "Phone")

                Column(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f), verticalArrangement = Arrangement.Center) {
                    Separator()
                }

                Row() {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                        DropdownField(title ="Vehicle Type", options = listOf("Commercial","Personal"))
                    }
                    Box(Modifier.width(10.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)) {
                        DropdownField(title ="Kind of Vehicle", options = listOf("Car","Bus", "Trailer", "Motorcycle").sorted())
                    }
                }



                MyCoverButton("Continue", {})
            }
        }
    })
}