package com.example.calendar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface DAO {
    @Dao
    interface CalendarEventDao {
        @Query("SELECT * FROM calendar_events")
        fun getAll(): List<CalendarEvent>

        @Insert
        fun insertAll(vararg events: CalendarEvent)

        @Delete
        fun delete(event: CalendarEvent)
    }
}