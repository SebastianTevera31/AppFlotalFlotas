package com.rfz.appflotalflotas.di.app

import com.rfz.appflotalflotas.data.model.login.response.AppFlotalMapper


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppFlotalMapper(): AppFlotalMapper {
        return AppFlotalMapper()
    }


}