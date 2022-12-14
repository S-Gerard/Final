package com.example.assignment.data

import androidx.room.*
import com.example.assignment.model.Assignment
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {

    @Query("SELECT * from assignment_database ORDER BY days * 1 ASC")
    fun getAssignments(): Flow<List<Assignment>>
    @Query("SELECT * FROM assignment_database WHERE id = :id")
    fun getAssignment(id:Long):Flow<Assignment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(assignment: Assignment)
    @Update
    fun update(assignment: Assignment)
    @Delete
    fun delete(assignment: Assignment)
}
