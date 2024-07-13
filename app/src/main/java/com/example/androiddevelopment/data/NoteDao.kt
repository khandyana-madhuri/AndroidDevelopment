package com.example.androiddevelopment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNotes(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY ID ASC")
    fun getAllNotes() : LiveData<List<Note>>
}