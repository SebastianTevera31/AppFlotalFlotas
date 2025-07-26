package com.rfz.appflotalflotas.data.network.service.languaje

import com.rfz.appflotalflotas.data.model.languaje.LanguageResponse

import com.rfz.appflotalflotas.data.network.client.languaje.LanguajeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class LanguajeService @Inject constructor(private val languajeClient: LanguajeClient) {
    suspend fun dolanguaje(tok:String, lang:String): Response<LanguageResponse> {
        return withContext(Dispatchers.IO) {
            languajeClient.doLanguaje(tok,lang)
        }
    }
}