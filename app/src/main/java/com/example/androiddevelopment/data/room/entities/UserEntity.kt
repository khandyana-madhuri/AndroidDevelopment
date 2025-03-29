package com.example.androiddevelopment.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("userId") val userId: String?,
    @ColumnInfo("display_Name") val displayName: String?,
    @ColumnInfo("email") val email: String?,
    @ColumnInfo("photo_Url") val photoUrl: String?
)