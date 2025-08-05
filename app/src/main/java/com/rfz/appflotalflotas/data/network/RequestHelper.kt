package com.rfz.appflotalflotas.data.network

import android.util.Log
import retrofit2.Response

suspend fun <T> requestHelper(endpointName: String = "", request: suspend () -> Response<T>) {
    try {
        val response = request()
        Log.d(endpointName, "${response.code()}")
        if (response.isSuccessful) {
            Log.d(endpointName, response.message())
            Log.d(endpointName, "${response.body()}")
        }
    } catch (e: Exception) {
        Log.e(endpointName, "${e.message}")
    }
}