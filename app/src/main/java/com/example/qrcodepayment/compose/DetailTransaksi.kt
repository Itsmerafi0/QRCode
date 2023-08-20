package com.example.qrcodepayment.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.qrcodepayment.controller.MainActivity
import com.example.qrcodepayment.controller.Payment
import com.example.qrcodepayment.presentation.MainViewModel
import com.example.qrcodepayment.presentation.TableCell



@Composable
fun DetailTransaksi(viewModel: MainViewModel = hiltViewModel(), data: String?,  scannedData: String? ) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 0.dp, horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://upload.wikimedia.org/wikipedia/id/thumb/5/55/BNI_logo.svg/2560px-BNI_logo.svg.png"),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(0.dp),
        )
        Divider(thickness = 1.dp)

        Text(text = "Transaksi",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        val column1Weight = .3f // 70%
        val column2Weight = .3f // 30%

        Column(Modifier.weight(1f)) {
            LazyColumn(Modifier.fillMaxSize()) {
                item {
                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Nama Merchant", weight = column1Weight)
                        data?.let {
                            TableCell(text = ": ${it.split(".")[2]}", weight = column2Weight)
                        }
                    }
                    Divider(thickness = 1.dp)

                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "Nominal Transaksi", weight = column1Weight)
                        data?.let {
                            TableCell(text = ": IDR ${it.split(".")[3]}", weight = column2Weight)
                        }
                    }
                    Divider(thickness = 1.dp)

                    Row(Modifier.fillMaxWidth()) {
                        TableCell(text = "ID Transaksi", weight = column1Weight)
                        data?.let {
                            TableCell(text = ": ${it.split(".")[1]}", weight = column2Weight)
                        }
                    }
                    Divider(thickness = 1.dp)
                }
            }
        }

        Button(
            onClick = {
                val nonNullableScannedData = scannedData ?: ""
                viewModel.saveToDatabase(nonNullableScannedData)

                val newAmount = viewModel.money.value - (data?.split(".")?.get(3)?.toInt() ?: 0)
                viewModel.updateMoney(newAmount)

                val intent = Intent(context, Payment::class.java)
                intent.putExtra("scannedData", nonNullableScannedData)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20),
        ) {
            Box {
                Text(
                    text = "Bayar",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth().padding(bottom = 15.dp), // Add some top padding
            shape = RoundedCornerShape(20),
        ) {
            Box {
                Text(
                    text = "Kembali",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

