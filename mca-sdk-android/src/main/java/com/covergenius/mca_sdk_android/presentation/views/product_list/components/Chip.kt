package com.covergenius.mca_sdk_android.presentation.views.product_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.covergenius.mca_sdk_android.presentation.theme.colorGrey
import com.covergenius.mca_sdk_android.presentation.theme.colorPrimary
import com.covergenius.mca_sdk_android.presentation.theme.colorSpaceGray
import com.covergenius.mca_sdk_android.presentation.theme.colorWhite
import com.covergenius.mca_sdk_android.presentation.views.product_list.Filter

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