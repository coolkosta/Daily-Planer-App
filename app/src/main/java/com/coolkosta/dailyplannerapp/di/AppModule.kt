package com.coolkosta.dailyplannerapp.di

import android.content.Context
import androidx.room.Room
import com.coolkosta.dailyplannerapp.data.local.ScheduleDao
import com.coolkosta.dailyplannerapp.data.local.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
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
}