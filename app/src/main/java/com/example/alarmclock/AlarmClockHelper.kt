package com.example.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.alarmclock.data.Alarm
import java.util.*
import android.content.BroadcastReceiver
import com.example.alarmclock.data.AlarmEntity
import com.example.alarmclock.screen.AlarmRingingScreen
import com.example.alarmclock.ui.theme.AlarmClockTheme

fun scheduleAlarm(context: Context, alarm: AlarmEntity) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("ALARM_ID", alarm.id)
        putExtra("ALARM_TONE", alarm.tone)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, alarm.hour)
        set(Calendar.MINUTE, alarm.minute)
        set(Calendar.SECOND, 0)

        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

fun cancelAlarm(context: Context, alarm: AlarmEntity) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarm.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)
}

// AlarmReceiver.kt

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getIntExtra("ALARM_ID", 0)
        val alarmTone = intent.getStringExtra("ALARM_TONE") ?: "Default"

        val alarmIntent = Intent(context, AlarmRingingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("ALARM_ID", alarmId)
            putExtra("ALARM_TONE", alarmTone)
        }
        context.startActivity(alarmIntent)
    }
}

// AlarmRingingActivity.kt
class AlarmRingingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmId = intent.getIntExtra("ALARM_ID", 0)
        val alarmTone = intent.getStringExtra("ALARM_TONE") ?: "Default"

        setContent {
            AlarmClockTheme {
                AlarmRingingScreen(
                    onDismiss = { finish() },
                    onSnooze = {
                        snoozeAlarm(this, alarmId, alarmTone)
                        finish()
                    }
                )
            }
        }
    }
}

fun snoozeAlarm(context: Context, alarmId: Int, alarmTone: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("ALARM_ID", alarmId)
        putExtra("ALARM_TONE", alarmTone)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarmId + 10000,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        add(Calendar.MINUTE, 10)
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

