package com.example.alarmclock.data

data class Alarm(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean = true,
    val tone: String = "Default",
    val label: String = ""
)