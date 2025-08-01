package com.rfz.appflotalflotas.domain.database

import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity
import com.rfz.appflotalflotas.data.repository.database.FscSoftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: FscSoftRepository
) {
    suspend operator fun invoke(): Flow<List<AppFlotalEntity>> {
        return repository.getTasks()
    }
}

