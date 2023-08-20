package com.example.qrcodepayment.controller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.qrcodepayment.compose.DetailTransaksi
import com.example.qrcodepayment.ui.theme.QrCodePaymentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Detail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Mengambil Data berupa String
        val scannedData = intent.getStringExtra("scannedData")

        setContent {
            DetailTransaksi(data = scannedData, scannedData = scannedData)
            QrCodePaymentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailTransaksi(data = scannedData, scannedData = scannedData) // Menampilkan komponen DetailTransaksi
                }
            }
        }
    }
}
