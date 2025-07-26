package com.rfz.appflotalflotas.domain.database


import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity
import com.rfz.appflotalflotas.data.repository.FscSoftRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val appFlotalRepository: FscSoftRepository) {
    suspend operator fun invoke(appFlotalEntity: AppFlotalEntity) {
        appFlotalRepository.addTask(appFlotalEntity)
    }
}