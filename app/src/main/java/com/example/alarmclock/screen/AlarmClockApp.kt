package com.example.alarmclock.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmclock.cancelAlarm
import com.example.alarmclock.data.Alarm
import com.example.alarmclock.scheduleAlarm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmClockApp() {
    var alarms by remember { mutableStateOf(listOf<Alarm>()) }
    var showAddAlarmDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Alarm Clock",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddAlarmDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, "Add Alarm")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CurrentTimeCard()

            Spacer(modifier = Modifier.height(16.dp))

            if (alarms.isEmpty()) {
                EmptyAlarmsView()
            } else {
                AlarmsList(
                    alarms = alarms,
                    onToggleAlarm = { alarm ->
                        alarms = alarms.map {
                            if (it.id == alarm.id) {
                                val updated = it.copy(isEnabled = !it.isEnabled)
                                if (updated.isEnabled) {
                                    scheduleAlarm(context, updated)
                                } else {
                                    cancelAlarm(context, updated)
                                }
                                updated
                            } else it
                        }
                    },
                    onDeleteAlarm = { alarm ->
                        cancelAlarm(context, alarm)
                        alarms = alarms.filter { it.id != alarm.id }
                    }
                )
            }
        }
    }

    if (showAddAlarmDialog) {
        AddAlarmDialog(
            onDismiss = { showAddAlarmDialog = false },
            onAlarmSet = { hour, minute, tone, label ->
                val newAlarm = Alarm(
                    id = System.currentTimeMillis().toInt(),
                    hour = hour,
                    minute = minute,
                    tone = tone,
                    label = label
                )
                alarms = alarms + newAlarm
                scheduleAlarm(context, newAlarm)
                showAddAlarmDialog = false
            }
        )
    }
}