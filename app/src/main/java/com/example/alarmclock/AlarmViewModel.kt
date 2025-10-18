package com.example.alarmclock

// AlarmViewModel.kt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.data.AlarmDatabase
import com.example.alarmclock.data.AlarmEntity
import com.example.alarmclock.data.AlarmRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlarmRepository
    val allAlarms: StateFlow<List<AlarmEntity>>

    init {
        val alarmDao = AlarmDatabase.getDatabase(application).alarmDao()
        repository = AlarmRepository(alarmDao)
        allAlarms = repository.allAlarms.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun insertAlarm(alarm: AlarmEntity, onSuccess: (Int) -> Unit = {}) {
        viewModelScope.launch {
            val id = repository.insertAlarm(alarm).toInt()
            onSuccess(id)
        }
    }

    fun updateAlarm(alarm: AlarmEntity) {
        viewModelScope.launch {
            repository.updateAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: AlarmEntity) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
        }
    }

    fun toggleAlarm(alarm: AlarmEntity) {
        viewModelScope.launch {
            repository.updateAlarm(alarm.copy(isEnabled = !alarm.isEnabled))
        }
    }

    suspend fun getAlarmById(id: Int): AlarmEntity? {
        return repository.getAlarmById(id)
    }
}