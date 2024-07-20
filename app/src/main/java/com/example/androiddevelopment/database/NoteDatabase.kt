package com.example.androiddevelopment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androiddevelopment.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile // changes made by one thread immediately reflects other threads.
        private var instance: NoteDatabase? = null
        private val LOCK = Any()

        // only one thread can execute the code inside the block at a time
        // invoke function follows singleton pattern. ensures that only one instance is created
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, NoteDatabase::class.java, "note_db"
        ).build()
    }
}