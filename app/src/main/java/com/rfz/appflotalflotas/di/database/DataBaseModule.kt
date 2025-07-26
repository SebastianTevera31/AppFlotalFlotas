package com.rfz.appflotalflotas.di.database

import android.content.Context
import androidx.room.Room
import com.rfz.appflotalflotas.data.dao.AppFlotalDao
import com.rfz.appflotalflotas.data.database.AppFlotalDatabase

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
    fun provideGasMonSoftDao(FscSoftDatabase: AppFlotalDatabase): AppFlotalDao {
        return FscSoftDatabase.flotalSoftDao()
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