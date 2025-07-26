package com.rfz.appflotalflotas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rfz.appflotalflotas.data.dao.AppFlotalDao
import com.rfz.appflotalflotas.data.model.flotalSoft.AppFlotalEntity

@Database(entities = arrayOf(AppFlotalEntity::class), version=1,exportSchema = false)
abstract class AppFlotalDatabase : RoomDatabase() {

    abstract fun flotalSoftDao(): AppFlotalDao

}