package com.rfz.appflotalflotas.domain.database

import com.rfz.appflotalflotas.data.model.flotalSoft.SensorTpmsEntity
import com.rfz.appflotalflotas.data.repository.database.SensorTableRepository
import javax.inject.Inject

class SensorTableUseCase @Inject constructor(private val sensorTableRepository: SensorTableRepository) {

    suspend fun doInsert(
        sensorTpmsEntity: SensorTpmsEntity
    ) = sensorTableRepository.insert(sensorTpmsEntity)

    suspend fun doGetUnsentRecords(
        userId: Int,
    ) = sensorTableRepository.getUnsentRecords(userId)

    suspend fun doGetLastRecord(userId: Int): SensorTpmsEntity? {
        return sensorTableRepository.getLastRecord(userId)
    }

    suspend fun doExist(sensorId: String): Boolean {
        return sensorTableRepository.exist(sensorId)
    }
}