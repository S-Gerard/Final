package com.example.assignment.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignment_database")
data class Assignment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val address: String,
    @NonNull @ColumnInfo(name="in_season")
    val inSeason: Boolean,
    val notes: String?
)
