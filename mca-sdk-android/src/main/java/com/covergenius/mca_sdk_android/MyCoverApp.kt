package com.covergenius.mca_sdk_android

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.ui.theme.colorBackground
import com.covergenius.mca_sdk_android.views.ProductsPage
import com.covergenius.mca_sdk_android.views.Routes
import com.covergenius.mca_sdk_android.views.auto.AutoInsuranceForm

@Composable
fun MyCoverApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Auto.name,
        Modifier.background(colorBackground)
    ) {
        composable(route = Routes.Products.name) {
            ProductsPage(onItemClicked = {l -> navController.navigate(Routes.Auto.name)})
        }
        composable(route = Routes.Auto.name) {
            AutoInsuranceForm()
        }
    }

}