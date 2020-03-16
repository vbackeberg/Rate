package com.valerian.rate.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.valerian.rate.CURRENT_DEPARTMENT_ID
import com.valerian.rate.SELECTED_IDS
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.Importance
import com.valerian.rate.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PositionsVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val competencyAreaDao = database.competencyAreaDao()
    private val importanceDao = database.importanceDao()
    private val positionDao = database.positionDao()

    private val departmentId = application
        .getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE)
        .getLong(CURRENT_DEPARTMENT_ID, 0L)

    fun getAll(): LiveData<List<Position>> {
        return positionDao.findAllByDepartment(departmentId)
    }

    fun newPosition(name: String) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val positionId = positionDao.insert(Position(0L, departmentId, name))
            val importances = mutableListOf<Importance>()
            competencyAreaDao.findAllIds().forEach { competencyAreaId ->
                importances.add(Importance(positionId, competencyAreaId, 0))
            }
            importanceDao.insertMany(importances)
        }
    }

    fun update(position: Position) {
        viewModelScope.launch { positionDao.update(position) }
    }

    fun delete(position: Position) {
        viewModelScope.launch { positionDao.delete(position) }
    }
}