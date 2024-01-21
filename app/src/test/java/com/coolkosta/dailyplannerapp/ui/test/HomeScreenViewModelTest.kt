package com.coolkosta.dailyplannerapp.ui.test

import com.coolkosta.dailyplannerapp.model.Schedule
import com.coolkosta.dailyplannerapp.repository.ScheduleRepository
import com.coolkosta.dailyplannerapp.screen.home.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeScreenViewModelTest {

    @Mock
    lateinit var scheduleRepository: ScheduleRepository

    private lateinit var viewModel: HomeScreenViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeScreenViewModel(scheduleRepository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInsertSchedule() = runBlockingTest {
        val schedule = Schedule(date = "2022-01-01", time = "09:00", isDone = false)
        viewModel.insertSchedule(schedule)
        verify(scheduleRepository).insertSchedule(schedule)
    }

    @Test
    fun testUpdateSchedule() = runBlockingTest {
        val schedule = Schedule(date = "2022-01-01", time = "09:00", isDone = false)
        viewModel.updateSchedule(schedule)
        verify(scheduleRepository).updateSchedule(schedule)
    }

    @Test
    fun testDeleteSchedule() = runBlockingTest {
        val schedule = Schedule(date = "2022-01-01", time = "09:00", isDone = false)
        viewModel.deleteSchedule(schedule)
        verify(scheduleRepository).deleteSchedule(schedule)
    }
}