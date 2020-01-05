package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Applicant
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val database = CompetencyAreasDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val positionDao = database.positionDao()

    private val departmentId = getApplication<Application>()
        .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
        .getLong(CURRENT_DEPARTMENT_ID, 0L)

    private val positionId = getApplication<Application>()
        .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    fun getAll(): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }

    fun new() = CoroutineScope(Dispatchers.IO).launch {
        applicantDao.insert(Applicant(0L, positionId, departmentId))
    }

    fun get(): LiveData<Position> {
        return positionDao.findById(positionId)
    }

    fun update(position: Position) = CoroutineScope(Dispatchers.IO).launch {
        positionDao.update(position)
    }
}