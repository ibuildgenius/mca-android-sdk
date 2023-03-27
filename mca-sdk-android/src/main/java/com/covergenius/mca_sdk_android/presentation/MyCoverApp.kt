package com.covergenius.mca_sdk_android.presentation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.covergenius.mca_sdk_android.Credentials
import com.covergenius.mca_sdk_android.presentation.theme.colorBackground
import com.covergenius.mca_sdk_android.presentation.views.Routes
import com.covergenius.mca_sdk_android.presentation.views.payment.PaymentResult
import com.covergenius.mca_sdk_android.presentation.views.product.ProductInfoScreen
import com.covergenius.mca_sdk_android.presentation.views.product.ProductDetailsForm
import com.covergenius.mca_sdk_android.presentation.views.payment.PaymentScreen
import com.covergenius.mca_sdk_android.presentation.views.product_list.ProductListScreen

@Composable
fun MyCoverApp(token: String?) {
    val navController = rememberNavController()
    // val backStackEntry by navController.currentBackStackEntryAsState()

   // val context = LocalContext.current

    if (token.isNullOrEmpty()) return

    //context.writeString(API_TOKEN_STRING, token)

    Credentials.token = token

    NavHost(
        navController = navController,
        startDestination = Routes.ProductList,
        Modifier.background(colorBackground)
    ) {
        composable(route = Routes.ProductList) {
            ProductListScreen(navController = navController)
        }
        composable(
            route = Routes.ProductInfo
        ) {
            ProductInfoScreen(onContinuePressed = { navController.navigate(Routes.ProductForms) }, navigator = navController)
        }

        composable(route = Routes.ProductForms) {
            ProductDetailsForm(navigator = navController, onContinuePressed =  { navController.navigate(Routes.Payment) }, )
        }

        composable(route = Routes.Payment) {
            PaymentScreen( navigator = navController)
        }

        composable(route = Routes.PaymentResult) {
            PaymentResult {  navController.navigate(Routes.ProductList) {
                popUpTo(Routes.PaymentResult) {
                    inclusive = true
                }
            } }
        }

        composable(route = Routes.PurchaseComplete) {

        }
    }

}