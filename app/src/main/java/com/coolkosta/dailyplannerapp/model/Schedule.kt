package com.coolkosta.dailyplannerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule_table")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val time: String,
    val tittle: String = "",
    val description: String = "",
    val isDone: Boolean,
)

