package com.example.assignment

import android.app.Application
import com.example.assignment.data.AssignmentDatabase

class BaseApplication : Application() {

    val database:AssignmentDatabase by lazy {AssignmentDatabase.getDatabase(this)}
}