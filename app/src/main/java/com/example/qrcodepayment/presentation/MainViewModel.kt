package com.example.qrcodepayment.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrcodepayment.controller.Detail
import com.example.qrcodepayment.data.models.ScannedData
import com.example.qrcodepayment.data.models.ScannedDataDao
import com.example.qrcodepayment.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: MainRepo,
    private val scannedDataDao: ScannedDataDao,
    private val context: Context

    ):ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    //DataList di LazyColumn Using Dao
    val listData: LiveData<List<ScannedData>> = scannedDataDao.getAll()

    //Sharedpreferences Money
    private val _money = MutableStateFlow(getMoneyFromSharedPreferences())
    val money: StateFlow<Int> = _money

    //Melakukan Scanning Data
    fun startScanning() {
        viewModelScope.launch {
            val scannedData = repo.startScanning().firstOrNull()
            if (!scannedData.isNullOrBlank()) {
                Log.d("Scan Data", "Scanned data: $scannedData") // Tambahkan baris ini untuk melacak data yang berhasil dipindai
                val intent = Intent(context, Detail::class.java)
                intent.putExtra("scannedData", scannedData)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    //SaveDatabase Room
    fun saveToDatabase(details: String) {
        viewModelScope.launch {
            val qrDataList = details.split(".")
            Log.d("Save Data", "$details")
            if (qrDataList.size >= 4) {
                val bank = qrDataList[0].substringAfter(":").trim()
                val name = qrDataList[2].substringAfter(":").trim()
                val transaction = qrDataList[3].substringAfter(":").trim()

                val insertedId = scannedDataDao.insertScannedData(
                    ScannedData(bank = bank, name = name, transaction = transaction)
                )
            }
        }
    }

    //Pemanggilan Data LazyColumn
    fun fetchAllData() {
        viewModelScope.launch {
            val allData = withContext(Dispatchers.IO) {
                scannedDataDao.getAll()
            }
            // Tambahkan Log.d statement untuk mencetak nilai dari variabel allData
            Log.d("Check Data", "allData: $allData")
        }
    }

    //SharedPrefences Money
    private fun getMoneyFromSharedPreferences(): Int {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("money", 1500000)
    }

    //updateMoney Setiap Melakukan Scann Uang Berkurang
    fun updateMoney(newAmount: Int) {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("money", newAmount).apply()
        _money.value = newAmount
    }

}
