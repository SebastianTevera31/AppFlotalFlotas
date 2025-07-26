package com.rfz.appflotalflotas.core.util

sealed class Resource<out T:Any>{
    data class Success<out T:Any> (val data:T): com.rfz.appflotalflotas.core.util.Resource<T>()
    data class Error(val errorMessage:String): com.rfz.appflotalflotas.core.util.Resource<Nothing>()
    data class Loading<out T:Any>(val data:T? = null, val message:String? = null):
        com.rfz.appflotalflotas.core.util.Resource<T>()
}