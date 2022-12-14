package com.example.assignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment.model.Assignment

@Database(entities = [Assignment::class], version = 1, exportSchema = false)
abstract class AssignmentDatabase : RoomDatabase() {
    abstract fun assignmentDao(): AssignmentDao

    companion object {
        @Volatile
        private var INSTANCE: AssignmentDatabase? = null

        fun getDatabase(context: Context): AssignmentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AssignmentDatabase::class.java,
                    "assignment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
