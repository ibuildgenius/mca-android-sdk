package com.covergenius.mca_sdk_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.ui.theme.McasdkandroidTheme
import com.covergenius.mca_sdk_android.ui.theme.colorBackground
import com.covergenius.mca_sdk_android.views.MyCoverRoutes
import com.covergenius.mca_sdk_android.views.ProductsPage

@Composable
fun MyCoverApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = MyCoverRoutes.Products.name,
    ) {
        composable(route = MyCoverRoutes.Products.name) {
            ProductsPage()
        }
    }

}