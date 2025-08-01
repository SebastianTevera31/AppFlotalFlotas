package com.rfz.appflotalflotas.di.network


import android.app.Application
import android.content.Context
import com.rfz.appflotalflotas.core.network.NetworkConfig
import com.rfz.appflotalflotas.data.network.client.assembly.AssemblyClient
import com.rfz.appflotalflotas.data.network.client.languaje.LanguajeClient
import com.rfz.appflotalflotas.data.network.client.login.LoginClient
import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothRepository
import com.rfz.appflotalflotas.data.repository.bluetooth.BluetoothRepositoryImp
import com.rfz.appflotalflotas.data.repository.wifi.WifiRepository
import com.rfz.appflotalflotas.data.repository.wifi.WifiRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.Base_Url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit): LoginClient {
        return retrofit.create(LoginClient::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideLanguajeClient(retrofit: Retrofit): LanguajeClient {
        return retrofit.create(LanguajeClient::class.java)
    }

    @Singleton
    @Provides
    fun provideAssemblyClient(retrofit: Retrofit): AssemblyClient {
        return retrofit.create(AssemblyClient::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BluetoothModule() {

    @Binds
    @Singleton
    abstract fun provideBluetoothModule(impl: BluetoothRepositoryImp): BluetoothRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class WifiModule {

    @Binds
    @Singleton
    abstract fun bindWifiModule(impl: WifiRepositoryImp): WifiRepository
}
