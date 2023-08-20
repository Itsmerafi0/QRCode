package com.example.qrcodepayment.data.models

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScannedData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scannedDataDao(): ScannedDataDao
}

