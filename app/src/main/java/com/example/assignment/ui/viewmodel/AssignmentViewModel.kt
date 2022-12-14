package com.example.assignment.ui.viewmodel

import androidx.lifecycle.*
import com.example.assignment.data.AssignmentDao
import com.example.assignment.model.Assignment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AssignmentViewModel(
  private val assignmentDao:AssignmentDao
): ViewModel() {

        val assignments:LiveData<List<Assignment>> = assignmentDao.getAssignments().asLiveData()
    fun assignment(id: Long):LiveData<Assignment> {
        return assignmentDao.getAssignment(id).asLiveData()
    }

    fun addAssignment(
        name: String,
        type: String,
        days: String,
        notes: String
    ) {
        val assignment = Assignment(
            name = name,
            type = type,
            days = days,
            notes = notes
        )

    viewModelScope.launch(Dispatchers.IO) {
        assignmentDao.insert(assignment)
    }
    }

    fun updateAssignment(
        id: Long,
        name: String,
        type: String,
        days: String,
        notes: String
    ) {
        val assignment = Assignment(
            id = id,
            name = name,
            type = type,
            days = days,
            notes = notes
        )
        viewModelScope.launch(Dispatchers.IO) {
            assignmentDao.update(assignment)
        }
    }

    fun deleteAssignment(assignment: Assignment) {
        viewModelScope.launch(Dispatchers.IO) {
            assignmentDao.delete(assignment)
        }
    }

    fun isValidEntry(name: String, days: String): Boolean {
        return name.isNotBlank() && days.isNotBlank()
    }
}

class AssignmentViewModelFactory(private val assignmentDao: AssignmentDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssignmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssignmentViewModel(assignmentDao) as T
        }
        throw IllegalArgumentException("Unexpected class: $modelClass")
    }
}
