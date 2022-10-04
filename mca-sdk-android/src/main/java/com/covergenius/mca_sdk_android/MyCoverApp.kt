package com.covergenius.mca_sdk_android

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.domain.model.Product
import com.covergenius.mca_sdk_android.views.theme.colorBackground
import com.covergenius.mca_sdk_android.views.Routes
import com.covergenius.mca_sdk_android.views.payment.PaymentResult
import com.covergenius.mca_sdk_android.views.product.ProductInfoScreen
import com.covergenius.mca_sdk_android.views.product.ProductDetailsForm
import com.covergenius.mca_sdk_android.views.payment.PaymentScreen
import com.covergenius.mca_sdk_android.views.product.ProductListScreen

@Composable
fun MyCoverApp() {
    val navController = rememberNavController()
    // val backStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.ProductList,
        Modifier.background(colorBackground)
    ) {
        composable(route = Routes.ProductList) {
            ProductListScreen(onItemClicked = { navController.navigate(Routes.ProductInfo) })
        }
        composable(route = Routes.ProductInfo) {
            ProductInfoScreen(onContinuePressed = { navController.navigate(Routes.ProductForms) })
        }

        composable(route = Routes.ProductForms) {
            ProductDetailsForm(Product()) { navController.navigate(Routes.Payment) }
        }

        composable(route = Routes.Payment) {
            PaymentScreen {
                navController.navigate(Routes.PaymentResult)
            }
        }

        composable(route = Routes.PaymentResult) {
            PaymentResult { navController.popBackStack(Routes.ProductList, false) }
        }
    }

}