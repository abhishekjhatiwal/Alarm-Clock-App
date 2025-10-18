package com.example.alarmclock.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmDialog(
    onDismiss: () -> Unit,
    onAlarmSet: (Int, Int, String, String) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var selectedTone by remember { mutableStateOf("Default") }
    var label by remember { mutableStateOf("") }
    var showTonePicker by remember { mutableStateOf(false) }

    val tones = listOf("Default", "Gentle", "Loud", "Beep", "Melody")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Alarm") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = {
                            selectedHour = (selectedHour + 1) % 24
                        }) {
                            Icon(Icons.Default.KeyboardArrowUp, "Increase")
                        }
                        Text(
                            text = String.format("%02d", selectedHour),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {
                            selectedHour = if (selectedHour == 0) 23 else selectedHour - 1
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, "Decrease")
                        }
                    }

                    Text(
                        text = ":",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = {
                            selectedMinute = (selectedMinute + 1) % 60
                        }) {
                            Icon(Icons.Default.KeyboardArrowUp, "Increase")
                        }
                        Text(
                            text = String.format("%02d", selectedMinute),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {
                            selectedMinute = if (selectedMinute == 0) 59 else selectedMinute - 1
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, "Decrease")
                        }
                    }
                }

                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTonePicker = !showTonePicker }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Alarm Tone", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                            Text(selectedTone, fontSize = 16.sp)
                        }
                        Icon(
                            if (showTonePicker) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }

                AnimatedVisibility(visible = showTonePicker) {
                    Column {
                        tones.forEach { tone ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTone = tone
                                        showTonePicker = false
                                    }
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedTone == tone,
                                    onClick = {
                                        selectedTone = tone
                                        showTonePicker = false
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(tone)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAlarmSet(selectedHour, selectedMinute, selectedTone, label)
                }
            ) {
                Text("Set Alarm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


































/*
@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmDialog(
    onDismiss: () -> Unit,
    onAlarmSet: (Int, Int, String, String) -> Unit
) {
    var selectedHour by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    var selectedTone by remember { mutableStateOf("Default") }
    var label by remember { mutableStateOf("") }
    var showTonePicker by remember { mutableStateOf(false) }

    val tones = listOf("Default", "Gentle", "Loud", "Beep", "Melody")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Alarm") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Time Picker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hour Picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = {
                            selectedHour = (selectedHour + 1) % 24
                        }) {
                            Icon(Icons.Default.KeyboardArrowUp, "Increase")
                        }
                        Text(
                            text = String.format("%02d", selectedHour),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {
                            selectedHour = if (selectedHour == 0) 23 else selectedHour - 1
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, "Decrease")
                        }
                    }

                    Text(
                        text = ":",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    // Minute Picker
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IconButton(onClick = {
                            selectedMinute = (selectedMinute + 1) % 60
                        }) {
                            Icon(Icons.Default.KeyboardArrowUp, "Increase")
                        }
                        Text(
                            text = String.format("%02d", selectedMinute),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {
                            selectedMinute = if (selectedMinute == 0) 59 else selectedMinute - 1
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, "Decrease")
                        }
                    }
                }

                // Label Input
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Label (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Tone Selector
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTonePicker = !showTonePicker }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Alarm Tone", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                            Text(selectedTone, fontSize = 16.sp)
                        }
                        Icon(
                            if (showTonePicker) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }

                AnimatedVisibility(visible = showTonePicker) {
                    Column {
                        tones.forEach { tone ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTone = tone
                                        showTonePicker = false
                                    }
                                    .padding(vertical = 12.dp, horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedTone == tone,
                                    onClick = {
                                        selectedTone = tone
                                        showTonePicker = false
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(tone)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAlarmSet(selectedHour, selectedMinute, selectedTone, label)
                }
            ) {
                Text("Set Alarm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

 */