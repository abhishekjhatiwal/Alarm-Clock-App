package com.example.alarmclock.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = true,
    val tone: String = "Default",
    val label: String = "",
    val createdAt: Long = System.currentTimeMillis()
)