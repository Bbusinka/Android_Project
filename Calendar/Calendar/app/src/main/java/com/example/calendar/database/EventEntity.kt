package com.example.calendar.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val place: String,
    val description: String,
    val startTime: String,
    val endTime: String
)