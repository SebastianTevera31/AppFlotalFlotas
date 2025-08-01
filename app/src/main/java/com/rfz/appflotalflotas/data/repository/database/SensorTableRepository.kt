package com.rfz.appflotalflotas.data.repository.database

import com.rfz.appflotalflotas.data.dao.SensorDao
import com.rfz.appflotalflotas.data.model.flotalSoft.SensorTpmsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SensorTableRepository @Inject constructor(
    private val sensorDao: SensorDao,
) {
    suspend fun insert(sensorTpmsEntity: SensorTpmsEntity) =
        sensorDao.insert(sensorTpmsEntity)

    suspend fun deleteOldRecords(vehicleId: Int) = sensorDao.deleteOldRecords(vehicleId)

    suspend fun getUnsentRecords(
        vehicleId: Int,
    ) = sensorDao.getUnsentRecords(vehicleId)

    suspend fun getLastRecord(vehicleId: Int): SensorTpmsEntity? =
        sensorDao.getLastRecord(vehicleId)

    suspend fun exist(sensorId: String): Boolean = sensorDao.exist(sensorId)
}