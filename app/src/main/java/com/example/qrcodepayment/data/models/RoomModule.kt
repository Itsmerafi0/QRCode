package com.example.qrcodepayment.data.models

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    fun provideScannedDataDao(database: AppDatabase): ScannedDataDao {
        return database.scannedDataDao()
    }
}
