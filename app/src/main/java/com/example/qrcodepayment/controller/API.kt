package com.example.qrcodepayment.controller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.qrcodepayment.compose.DetailPromo
import com.example.qrcodepayment.compose.DetailTransaksi
import com.example.qrcodepayment.compose.PromoList
import com.example.qrcodepayment.presentation.MainViewModel
import com.example.qrcodepayment.ui.theme.QrCodePaymentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class API : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QrCodePaymentTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = viewModel()

                NavHost(navController, startDestination = "promoList") {
                    composable("promoList") {
                        PromoList(navController = navController, viewModel = viewModel)
                    }
                    composable(
                        //promoDetail Click By Id
                        route = "promoDetail/{promoId}",
                        arguments = listOf(navArgument("promoId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val promoId = backStackEntry.arguments?.getString("promoId") ?: ""
                        DetailPromo(promoId = promoId, viewModel = viewModel)
                    }
                }
            }
        }
    }
}