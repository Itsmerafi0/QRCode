package com.example.qrcodepayment.data.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface ScannedDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScannedData(scannedData: ScannedData)

    @Query("SELECT * FROM scanned_data")
    fun getAll(): LiveData<List<ScannedData>>


}



