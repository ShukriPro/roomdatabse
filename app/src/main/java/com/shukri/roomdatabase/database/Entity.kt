package com.shukri.roomdatabase.database

import android.app.backup.BackupAgent
import androidx.room.Entity
import androidx.room.PrimaryKey

//user entity
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val age: Int
)