package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Department
import com.example.myapplication.entities.Importance
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PositionsVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val competencyAreaDao = database.competencyAreaDao()
    private val importanceDao = database.importanceDao()
    private val positionDao = database.positionDao()
    private val departmentDao = database.departmentDao()

    private val departmentId = application
        .getSharedPreferences(CURRENT_DEPARTMENT_ID, Context.MODE_PRIVATE)
        .getLong(CURRENT_DEPARTMENT_ID, 0L)

    fun getAll(): LiveData<List<Position>> {
        return positionDao.findAll()
    }

    fun newPosition(name: String) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val positionId = positionDao.insert(Position(0L, name))
            val importances = mutableListOf<Importance>()
            competencyAreaDao.findAllIds().forEach { competencyAreaId ->
                importances.add(Importance(positionId, competencyAreaId, 0))
            }
            importanceDao.insertMany(importances)
        }
    }

    fun update(position: Position) = CoroutineScope(Dispatchers.IO).launch {
        positionDao.update(position)
    }

    fun update(department: Department) = CoroutineScope(Dispatchers.IO).launch {
        departmentDao.update(department)
    }

    fun get(): LiveData<Department> {
        return departmentDao.findById(departmentId)
    }
}