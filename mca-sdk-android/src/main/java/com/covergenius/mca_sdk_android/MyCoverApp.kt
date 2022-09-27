package com.covergenius.mca_sdk_android

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.data.model.Product
import com.covergenius.mca_sdk_android.ui.theme.colorBackground
import com.covergenius.mca_sdk_android.views.ProductsPage
import com.covergenius.mca_sdk_android.views.Routes
import com.covergenius.mca_sdk_android.views.auto.AutoInsuranceForm
import com.covergenius.mca_sdk_android.views.auto.AutoPersonalDetailsForm
import com.covergenius.mca_sdk_android.views.payment.PaymentScreen

@Composable
fun MyCoverApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.Payment,
        Modifier.background(colorBackground)
    ) {
        composable(route = Routes.Products) {
            ProductsPage(onItemClicked = { l -> navController.navigate(Routes.Auto) })
        }
        composable(route = Routes.Auto) {
            AutoInsuranceForm(onContinuePressed = { navController.navigate(Routes.AutoForms)})
        }

        composable(route = Routes.AutoForms) {
            AutoPersonalDetailsForm(Product())
        }

        composable(route = Routes.Payment) {
            PaymentScreen()
        }
    }

}