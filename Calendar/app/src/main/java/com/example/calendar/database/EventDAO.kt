package com.example.calendar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDAO {

    @Query("SELECT * FROM events")
    fun getAll(): LiveData<List<EventEntity>>

    @Insert
    suspend fun insert(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM events WHERE title LIKE :title")
    suspend fun searchTitle(title: String): List<EventEntity>

    @Query("SELECT * FROM events WHERE startTime LIKE :date OR endTime LIKE :date")
    suspend fun searchDate(date: String): List<EventEntity>
}