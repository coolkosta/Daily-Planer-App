package com.coolkosta.dailyplannerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolkosta.dailyplannerapp.model.Schedule

@Database(
    entities = [Schedule::class],
    version = 1,
    exportSchema = false
)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract fun ScheduleDao() : ScheduleDao
}