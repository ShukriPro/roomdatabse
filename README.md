### Room Database Setup

## Step 1: Add the KSP Plugin to the Top-Level Build File

Add the following to the `build.gradle.kts` file in the top-level project directory:

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}
```

## Step 2: Add Plugins and Dependencies to the Module-Level Build File

In the `build.gradle.kts` file for the module, add the following:

### Plugins:

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}
```

### Dependencies:

```kotlin
dependencies {
    // Room database
    val room_version = "2.6.1"

    // Room components
    implementation("androidx.room:room-runtime:\$room_version")
    implementation("androidx.room:room-ktx:\$room_version") // Kotlin extensions for Room
    ksp("androidx.room:room-compiler:\$room_version") // KSP for annotation processing
}
```

## Step 3: Create the Entity (Model)

Define the data model using the `@Entity` annotation:

```kotlin
package com.shukri.roomdatabase.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
```

## Step 4: Create the DAO (Data Access Object)

Define the DAO interface with methods for interacting with the database:

```kotlin
package com.shukri.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>
}
```

## Step 5: Create the Database Setup

Add fallback to handle schema changes without crashing the app:

```kotlin
package com.shukri.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database setup
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            )
                .fallbackToDestructiveMigration() // Handle schema changes without crashing
                .build()
            INSTANCE = instance
            instance
        }
    }
}
```

