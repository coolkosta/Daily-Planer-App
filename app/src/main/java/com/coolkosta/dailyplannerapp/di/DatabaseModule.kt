package com.coolkosta.dailyplannerapp.di

import android.content.Context
import androidx.room.Room
import com.coolkosta.dailyplannerapp.data.local.ScheduleDao
import com.coolkosta.dailyplannerapp.data.local.ScheduleDatabase
import com.coolkosta.dailyplannerapp.data.local.ScheduleLocalRepositoryImpl
import com.coolkosta.dailyplannerapp.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideSchedule(schedule: ScheduleDatabase): ScheduleDao =
        schedule.ScheduleDao()

    @Singleton
    @Provides
    fun provideScheduleDatabase(@ApplicationContext context: Context): ScheduleDatabase =
        Room.databaseBuilder(
            context = context,
            ScheduleDatabase::class.java,
            name = "schedule_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideScheduleRepository(scheduleDao: ScheduleDao): ScheduleRepository {
        return ScheduleLocalRepositoryImpl(scheduleDao)
    }
}