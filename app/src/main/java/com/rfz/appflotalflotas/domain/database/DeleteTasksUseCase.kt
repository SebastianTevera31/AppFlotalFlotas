package com.rfz.appflotalflotas.domain.database

import com.rfz.appflotalflotas.data.repository.FscSoftRepository
import javax.inject.Inject

class DeleteTasksUseCase @Inject constructor(
    private val repository: FscSoftRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllTasks()
    }
}