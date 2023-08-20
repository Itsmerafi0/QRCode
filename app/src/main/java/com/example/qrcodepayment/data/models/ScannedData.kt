package com.example.qrcodepayment.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scanned_data")
data class ScannedData(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val bank: String,
    val name: String,
    val transaction: String,
    val date: Long // Tambahkan properti untuk menyimpan tanggal (misalnya dalam bentuk millisecond since epoch)
)


