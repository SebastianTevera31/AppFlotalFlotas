package com.rfz.appflotalflotas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rfz.appflotalflotas.data.dao.AppFlotalDao
import com.rfz.appflotalflotas.data.dao.SensorDao
import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity
import com.rfz.appflotalflotas.data.model.flotalSoft.SensorTpmsEntity

@Database(
    entities = arrayOf(AppFlotalEntity::class, SensorTpmsEntity::class),
    version = 1,
    exportSchema = false
)
abstract class AppFlotalDatabase : RoomDatabase() {
    abstract fun flotalSoftDao(): AppFlotalDao
    abstract fun sensorDao(): SensorDao
}