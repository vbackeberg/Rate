package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Importance
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PositionsVM(application: Application) : AndroidViewModel(application) {
    private val database = CompetencyAreasDatabase.getDatabase(application)
    private val competencyAreaDao = database.competencyAreaDao()
    private val importanceDao = database.importanceDao()
    private val positionDao = database.positionDao()

    fun getAll(): LiveData<List<Position>> {
        return positionDao.findAll()
    }

    fun new(position: Position) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val positionId = positionDao.insert(position)
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
}