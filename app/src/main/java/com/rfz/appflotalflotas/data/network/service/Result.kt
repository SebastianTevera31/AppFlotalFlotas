package com.rfz.appflotalflotas.data.network.service

class Result {
    sealed class Result<out T> {
        data class Success<out T>(val data: T) : com.rfz.appflotalflotas.data.network.service.Result.Result<T>()
        data class Error(val exception: Exception) : com.rfz.appflotalflotas.data.network.service.Result.Result<Nothing>()
    }
}