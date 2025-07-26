package com.rfz.appflotalflotas.domain.languaje


import com.rfz.appflotalflotas.data.model.languaje.LanguageResponse

import com.rfz.appflotalflotas.data.repository.languaje.LanguajeRepository
import javax.inject.Inject


class LanguajeUseCase @Inject constructor(
    private val languajeRepository: LanguajeRepository
) {
    suspend operator fun invoke(token: String, lang: String): Result<LanguageResponse> {
        return languajeRepository.dolanguaje(token, lang)
    }
}
