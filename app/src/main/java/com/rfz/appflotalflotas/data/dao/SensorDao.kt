package com.rfz.appflotalflotas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rfz.appflotalflotas.data.model.flotalSoft.SensorTpmsEntity

@Dao
interface SensorDao {
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(sensorTpms: SensorTpmsEntity)

    @Query("DELETE FROM sensorTpms WHERE (sensor_id, timestamp) NOT IN " +
                "(SELECT sensor_id, MAX(timestamp) FROM sensorTpms WHERE vehicle_id = :vehicleId " +
                "GROUP BY sensor_id) " +
                "AND vehicle_id = :vehicleId")
    suspend fun deleteOldRecords(vehicleId: Int)

    @Query("SELECT * FROM sensorTpms WHERE vehicle_id = :vehicleId AND sendStatus = 0")
    suspend fun getUnsentRecords(vehicleId: Int): List<SensorTpmsEntity?>

    @Query("SELECT * FROM sensorTpms WHERE vehicle_id = :vehicleId ORDER BY rowid DESC LIMIT 1")
    suspend fun getLastRecord(vehicleId: Int): SensorTpmsEntity?

    @Query("SELECT EXISTS (SELECT 1 FROM sensorTpms WHERE sensor_id = :sensorId)")
    suspend fun exist(sensorId: String): Boolean

    @Query("UPDATE sensorTpms SET sendStatus = :sendStatus WHERE vehicle_id = :vehicleId " +
            "AND timestamp = :timestamp")
    suspend fun setRecordStatus(vehicleId: Int, timestamp: String, sendStatus: Boolean)
}