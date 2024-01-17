package com.coolkosta.dailyplannerapp.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.dailyplannerapp.model.Schedule
import com.coolkosta.dailyplannerapp.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: ScheduleRepository) :
    ViewModel() {

    private val _scheduleListByDate = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleListByDate = _scheduleListByDate.asStateFlow()

    init {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val todayDay = LocalDateTime.now().format(formatter)
            repository.getScheduleByDate(todayDay).distinctUntilChanged()
        }
    }

    fun insertSchedule(schedule: Schedule) = viewModelScope.launch {
        repository.insertSchedule(schedule)
    }

    fun updateSchedule(schedule: Schedule) = viewModelScope.launch() {
        repository.updateSchedule(schedule)
    }

    fun getScheduleByDate(date: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.getScheduleByDate(date).distinctUntilChanged()
            .collect { scheduleList ->
                _scheduleListByDate.value = scheduleList
            }
    }

    fun deleteSchedule(schedule: Schedule) = viewModelScope.launch {
        repository.deleteSchedule(schedule)
    }

}