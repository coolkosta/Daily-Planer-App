package com.coolkosta.dailyplannerapp.data.local

import com.coolkosta.dailyplannerapp.model.Schedule
import com.coolkosta.dailyplannerapp.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleLocalRepositoryImpl(private val scheduleDao: ScheduleDao) : ScheduleRepository {

    override fun getSchedule(): Flow<List<Schedule>> =
        scheduleDao.getSchedule()

    override fun getScheduleByDate(date: String): Flow<List<Schedule>> =
        scheduleDao.getScheduleByDate(date)

    override suspend fun insertSchedule(schedule: Schedule) =
        scheduleDao.insertSchedule(schedule)

    override suspend fun deleteSchedule(schedule: Schedule) =
        scheduleDao.deleteSchedule(schedule)
}