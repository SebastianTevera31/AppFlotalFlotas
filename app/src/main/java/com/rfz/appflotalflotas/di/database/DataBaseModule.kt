package com.rfz.appflotalflotas.di.database

import android.content.Context
import androidx.room.Room
import com.rfz.appflotalflotas.data.dao.AppFlotalDao
import com.rfz.appflotalflotas.data.dao.SensorDao
import com.rfz.appflotalflotas.data.database.AppFlotalDatabase
import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity
import dagger.Binds

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    fun provideGasMonSoftDao(appFlotalDatabase: AppFlotalDatabase): AppFlotalDao {
        return appFlotalDatabase.flotalSoftDao()
    }

    @Provides
    fun provideSensorDao(appFlotalDatabase: AppFlotalDatabase): SensorDao {
        return appFlotalDatabase.sensorDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext _appContext: Context): AppFlotalDatabase {
        return Room.databaseBuilder(
            _appContext,
            AppFlotalDatabase::class.java,
            "AppFlotalFlotasDatabase"
        ).build()
    }
}