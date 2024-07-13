package com.example.androiddevelopment.data

import androidx.lifecycle.LiveData

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun addNotes(note: Note)  = noteDao.addNotes(note)
}