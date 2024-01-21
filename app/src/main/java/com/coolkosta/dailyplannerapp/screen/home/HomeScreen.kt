package com.coolkosta.dailyplannerapp.screen.home

import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coolkosta.dailyplannerapp.R
import com.coolkosta.dailyplannerapp.model.Schedule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val todayDate = LocalDateTime.now().format(dateFormatter)!!

    val scheduleListByDate = viewModel.scheduleListByDate.collectAsState()

    val date = remember { mutableStateOf(todayDate) }

    val isAddSchedule = remember { mutableStateOf(false) }

    val hour by remember { mutableIntStateOf(0) }
    val minute by remember { mutableIntStateOf(0) }

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true
    )

    if (isAddSchedule.value) {
        CalendarDialog(
            "add",
            null,
            isAddSchedule,
            timePickerState,
            date.value,
            viewModel
        )
    }

    Surface(
        color = Color.LightGray
    ) {
        Column {
            HorizontalCalendar(
                date,
                viewModel
            )

            Divider(
                modifier = Modifier
                    .height(1.dp),
                color = Color.Yellow
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TodayDateText(date)

                IconButton(onClick = {
                    isAddSchedule.value = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add button"
                    )
                }
            }

            ScheduleList(
                scheduleListByDate,
                timePickerState,
                viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleList(
    scheduleListByDate: State<List<Schedule>>,
    timePickerState: TimePickerState,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(scheduleListByDate.value.size) {
            ScheduleItem(
                scheduleListByDate.value[it],
                timePickerState,
                viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleItem(
    schedule: Schedule,
    timePickerState: TimePickerState,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
) {

    var openUpdateCalendarDialog by remember { mutableStateOf(false) }
    if (openUpdateCalendarDialog) {
        CalendarDialog(
            "update",
            schedule,
            null,
            timePickerState,
            schedule.date,
            viewModel
        )
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(
                horizontal = 5.dp,
                vertical = 3.dp
            ),
        border = BorderStroke(1.dp, Color.Yellow),
        colors = CardDefaults.cardColors(
            containerColor = if (schedule.isDone) {
                Color.Gray
            } else {
                Color.White
            }
        ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = schedule.isDone,
                onCheckedChange = {
                    viewModel.updateSchedule(
                        Schedule(
                            id = schedule.id,
                            isDone = !(schedule.isDone),
                            date = schedule.date,
                            time = schedule.time,
                            title = schedule.title,
                            description = schedule.description
                        )
                    )
                },
                modifier = Modifier
                    .weight(0.2f)
                    .padding(horizontal = 5.dp)
            )

            Text(
                text = schedule.time,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(0.5F)
                    .padding(start = 4.dp)
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = schedule.title,

                    )
                Text(
                    text = schedule.description,

                    )
            }

            IconButton(onClick = {
                openUpdateCalendarDialog = true
            }) {
                Icon(
                    Icons.Filled.Edit,
                    "Edit Schedule"
                )
            }

            IconButton(onClick = {
                viewModel.deleteSchedule(schedule)
            }) {
                Icon(
                    Icons.Filled.Clear,
                    "Remove Schedule"
                )
            }
        }
    }
}

@Composable
fun HorizontalCalendar(
    date: MutableState<String>,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        AndroidView(
            factory = {
                CalendarView(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        ) { calendarView ->
            calendarView.setOnDateChangeListener { _, year, month, day ->

                date.value = "$year-${(month + 1)}-$day"
                val monthString = (month + 1).toString().padStart(2, '0')
                val dayString = day.toString().padStart(2, '0')

                date.value = "$year-$monthString-$dayString"

                viewModel.getScheduleByDate(date.value)
            }
        }
    }
}

@Composable
fun TodayDateText(
    date: MutableState<String>,
    modifier: Modifier = Modifier,
) {
    Text(
        text = date.value,
        modifier = modifier
            .padding(10.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDialog(
    isAddOrUpdateSchedule: String,
    schedule: Schedule?,
    isAddSchedule: MutableState<Boolean>?,
    timePickerState: TimePickerState,
    date: String,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier,
) {
    var scheduleTitle by remember { mutableStateOf(schedule?.title ?: "") }
    var scheduleDescription by remember { mutableStateOf(schedule?.description ?: "") }
    val closeDialog = remember { mutableStateOf(false) }
    if (closeDialog.value) {
        return
    }

    AlertDialog(
        onDismissRequest = {
            closeDialog.value = true
            isAddSchedule?.value = false
        },
        modifier = modifier
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val scaleBy = 0.7f

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(width = screenWidth * scaleBy, height = screenHeight * scaleBy)
                .requiredSize(width = screenWidth, height = screenHeight)
                .scale(scaleBy)
        ) {
            when (isAddOrUpdateSchedule) {
                "add" -> {
                    Text(text = stringResource(id = R.string.add_text))
                }

                "update" -> {
                    Text(text = stringResource(id = R.string.update_text))
                }

                else -> {

                }
            }

            TimePicker(
                state = timePickerState,
            )

            TextField(
                value = scheduleTitle,
                onValueChange = {
                    scheduleTitle = it
                },
                placeholder = {
                    Text(stringResource(id = R.string.tittle_text))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            TextField(
                value = scheduleDescription,
                onValueChange = {
                    scheduleDescription = it
                },
                placeholder = {
                    Text(stringResource(id = R.string.description_text))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(onClick = {
                    closeDialog.value = true
                    isAddSchedule?.value = false
                }) {
                    Text(stringResource(id = R.string.cancel_text))
                }

                when (isAddOrUpdateSchedule) {
                    "add" -> {
                        Button(onClick = {
                            viewModel.insertSchedule(
                                Schedule(
                                    0,
                                    date,
                                    isDone = false,
                                    time = formatTime(timePickerState.hour, timePickerState.minute),
                                    title = scheduleTitle,
                                    description = scheduleDescription
                                )
                            )

                            isAddSchedule?.value = false
                            closeDialog.value = true
                        }) {
                            Text(stringResource(id = R.string.save_text))
                        }
                    }

                    "update" -> {
                        Button(onClick = {

                            viewModel.updateSchedule(
                                Schedule(
                                    id = schedule!!.id,
                                    isDone = schedule.isDone,
                                    date = date,
                                    time = formatTime(timePickerState.hour, timePickerState.minute),
                                    title = scheduleTitle,
                                    description = scheduleDescription
                                )
                            )

                            closeDialog.value = true
                        }) {
                            Text(stringResource(id = R.string.update_text))
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
}

fun formatTime(hour: Int, minute: Int): String {
    return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
}




