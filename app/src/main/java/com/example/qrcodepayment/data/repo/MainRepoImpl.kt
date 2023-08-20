package com.example.qrcodepayment.data.repo


import com.example.qrcodepayment.domain.repo.MainRepo
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,

) : MainRepo {

    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        val details = getDetails(it)
                        /*saveToDatabase(details) // Save data to Room database*/
                        send(details) // Send the details to the flow
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }


    //Mendapatkan Details saat melakukan Scan
    private fun getDetails(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_TEXT -> {
                val qrData = barcode.rawValue
                if (qrData != null) {
                    val qrDataList = qrData.split(".")
                    "$qrData"
                } else {
                    "QR code data is null"
                }
            }

            else -> {
                "Couldn't determine"
            }
        }
    }
}

/*  private suspend fun saveToDatabase(details: String) {
        val qrDataList = details.split(".")
        Log.d("Data Tersimpan","$details")
        if (qrDataList.size >= 4) {
            val bank = qrDataList[0].substringAfter(":").trim()
            val name = qrDataList[2].substringAfter(":").trim()
            val transaction = qrDataList[3].substringAfter(":").trim()

            val insertedId = database.scannedDataDao().insertScannedData(
                ScannedData(bank = bank, name = name, transaction = transaction)
            )

            Log.d("Data Tersimpan", "Data Room $insertedId: $bank, $name, $transaction")
        }
    }*/
