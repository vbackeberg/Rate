package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyAreaWithImportance
import com.example.myapplication.entities.Importance
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetencyAreasVM(application: Application) : AndroidViewModel(application) {
    private val database = CompetencyAreasDatabase.getDatabase(application)
    private val competencyAreaDao = database.competencyAreaDao()
    private val importanceDao = database.importanceDao()
    private val positionDao = database.positionDao()

    private val positionId = application
        .getSharedPreferences(CURRENT_POSITION_ID, Context.MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    fun getAll(): LiveData<List<CompetencyAreaWithImportance>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    fun new(name: String) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val competencyAreaId = competencyAreaDao.insert(CompetencyArea(0L, name))
            val importances = mutableListOf<Importance>()
            positionDao.findAllIds().forEach { positionId ->
                importances.add(Importance(positionId, competencyAreaId, 0))
            }
            importanceDao.insertMany(importances)
        }
    }

    fun update(importance: Importance) = CoroutineScope(Dispatchers.IO).launch {
        importanceDao.update(importance)
    }

    fun get(): LiveData<Position> {
        return positionDao.findById(positionId)
    }
}