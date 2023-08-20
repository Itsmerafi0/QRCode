package com.example.qrcodepayment.controller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.qrcodepayment.compose.DetailPromo
import com.example.qrcodepayment.compose.PromoList
import com.example.qrcodepayment.presentation.MainViewModel
import com.example.qrcodepayment.ui.theme.QrCodePaymentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromoDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val promoId = intent.getStringExtra("promoId") ?: ""

        setContent {
            QrCodePaymentTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "promoList") {
                    composable("promoList") {
                        // Use Hilt's viewModels property delegate to create the ViewModel instance
                        val mainViewModel: MainViewModel = hiltViewModel()

                        // Pass mainViewModel to PromoList
                        PromoList(navController = navController, viewModel = mainViewModel)
                    }
                    composable(
                        route = "promoDetail/{promoId}",
                        arguments = listOf(navArgument("promoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        // Retrieve promoId from the route arguments
                        val promoId = backStackEntry.arguments?.getString("promoId") ?: ""

                        // Display PromoDetailScreen and pass the promoId to the ViewModel
                        DetailPromo(promoId = promoId, viewModel = hiltViewModel())
                    }
                }
            }
        }
    }
}
