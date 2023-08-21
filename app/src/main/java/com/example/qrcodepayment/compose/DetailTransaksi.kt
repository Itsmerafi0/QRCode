package com.example.qrcodepayment.compose

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var isPaymentConfirmationVisible by remember { mutableStateOf(false) }
    var showCancelConfirmationDialog by remember { mutableStateOf(false) }
    var isPaymentCanceled by remember { mutableStateOf(false) }


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

        Text(
            text = "Transaksi",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
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
                isPaymentConfirmationVisible = true
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

        //Pop Konfirmasi Pembayaran
        if (isPaymentConfirmationVisible) {
            AlertDialog(
                onDismissRequest = { isPaymentConfirmationVisible = false },
                title = {
                    Text("Konfirmasi Pembayaran")
                },
                text = {
                    Text("Apakah Anda yakin ingin melakukan pembayaran?")
                },
                confirmButton = {
                    TextButton(onClick = {
                        val nonNullableScannedData = scannedData ?: ""
                        viewModel.saveToDatabase(nonNullableScannedData)

                        //Mengurangi Money
                        val newAmount =
                            viewModel.money.value - (data?.split(".")?.get(3)?.toInt() ?: 0)
                        viewModel.updateMoney(newAmount)

                        Toast.makeText(context,"Payment Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, Payment::class.java)
                        //Mengirim Data berupa String
                        intent.putExtra("scannedData", nonNullableScannedData)
                        context.startActivity(intent)

                        isPaymentConfirmationVisible = false
                    }) {
                        Text("Ya")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        isPaymentConfirmationVisible = false
                    }) {
                        Text("Batal")
                    }
                }
            )
        }

        Button(
            onClick = {
                showCancelConfirmationDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(20)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cancel",
                    textAlign = TextAlign.Center
                )
            }
        }

        // Show the cancel payment confirmation dialog if the state is true
        if (showCancelConfirmationDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when clicked outside the dialog
                    showCancelConfirmationDialog = false
                },
                title = {
                    Text(text = "Konfirmasi Pembatalan")
                },
                text = {
                    Text(text = "Apakah Anda yakin ingin membatalkan pembayaran ini?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            // Logic to handle cancellation
                            showCancelConfirmationDialog = false
                            isPaymentCanceled = true
                        }
                    ) {
                        Text(text = "Ya")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            // Dismiss the dialog without taking any action
                            showCancelConfirmationDialog = false
                        }
                    ) {
                        Text(text = "Tidak")
                    }
                }
            )
        }

        // Navigate to MainActivity if payment is canceled
        if (isPaymentCanceled) {
            Toast.makeText(context,"Payment Failed", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}


