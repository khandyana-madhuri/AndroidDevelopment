package com.example.androiddevelopment.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.androiddevelopment.database.NoteDatabase
import com.example.androiddevelopment.model.Note

class NoteRepository(private val db: NoteDatabase) {
    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNate(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNotes(query: String?) = db.getNoteDao().searchNote(query)
}