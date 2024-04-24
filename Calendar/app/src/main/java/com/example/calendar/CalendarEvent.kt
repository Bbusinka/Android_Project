package com.example.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "calendar_events")
        data class CalendarEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val location: String,
    val startTime: Long,
    val endTime: Long
)
