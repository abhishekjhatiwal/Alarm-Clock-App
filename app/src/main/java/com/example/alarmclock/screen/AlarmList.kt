package com.example.alarmclock.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarmclock.data.AlarmEntity

@Composable
fun AlarmsList(
    alarms: List<AlarmEntity>,
    onToggleAlarm: (AlarmEntity) -> Unit,
    onDeleteAlarm: (AlarmEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(alarms, key = { it.id }) { alarm ->
            AlarmItem(
                alarm = alarm,
                onToggle = { onToggleAlarm(alarm) },
                onDelete = { onDeleteAlarm(alarm) }
            )
        }
    }
}
