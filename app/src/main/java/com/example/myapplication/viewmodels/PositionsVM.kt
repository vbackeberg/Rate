package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.databases.AppDatabase
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

    fun getAll(departmentId: Long): LiveData<List<Position>> {
        return positionDao.findAllByDepartment(departmentId)
    }

    fun newPosition(name: String, departmentId: Long) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val positionId = positionDao.insert(Position(0L, departmentId, name))
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

    fun delete(position: Position) = CoroutineScope(Dispatchers.IO).launch {
        positionDao.delete(position)
    }
}