package com.example.calendar

import androidx.room.Database
import androidx.room.RoomDatabase

class CalendarEventDao {
    @Database(entities = [CalendarEvent::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun calendarEventDao(): CalendarEventDao
    }
}