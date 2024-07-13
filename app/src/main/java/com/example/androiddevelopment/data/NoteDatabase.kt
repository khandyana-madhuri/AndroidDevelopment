package com.example.androiddevelopment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        @Volatile  // volatile means that writes to this field are immediately made visible to other threads.
        private var instance : NoteDatabase ?= null

        fun getDatabase(context: Context): NoteDatabase {
            val tempInstance = instance
            if (tempInstance != null)
                return tempInstance
            else {
                synchronized(this) {
                    val db = Room.databaseBuilder(
                        context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database"
                    ).build()
                    instance = db
                    return db
                }
            }
        }
    }
}