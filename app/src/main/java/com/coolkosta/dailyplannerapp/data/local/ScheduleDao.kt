package com.coolkosta.dailyplannerapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.coolkosta.dailyplannerapp.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule_table")
    fun getSchedule(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedule_table WHERE date = :date ORDER BY time ASC")
    fun getScheduleByDate(date: String): Flow<List<Schedule>>

    @Insert
    suspend fun insertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)
}