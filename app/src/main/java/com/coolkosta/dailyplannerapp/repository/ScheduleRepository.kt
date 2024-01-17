package com.coolkosta.dailyplannerapp.repository

import com.coolkosta.dailyplannerapp.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedule(): Flow<List<Schedule>>
    fun getScheduleByDate(date: String): Flow<List<Schedule>>
    suspend fun insertSchedule(schedule: Schedule)
    suspend fun updateSchedule(schedule: Schedule)
    suspend fun deleteSchedule(schedule: Schedule)
}
