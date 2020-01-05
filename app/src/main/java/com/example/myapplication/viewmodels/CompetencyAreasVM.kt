package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

    fun getAll(positionId: Long): LiveData<List<CompetencyAreaWithImportance>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    fun new(competencyArea: CompetencyArea) = CoroutineScope(Dispatchers.IO).launch {
        CompetencyAreasDatabase.getDatabase(getApplication()).runInTransaction {
            val competencyAreaId = competencyAreaDao.insert(competencyArea)
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

    suspend fun get(id: Long): CompetencyArea {
        return competencyAreaDao.findById(id)
    }

    fun getPosition(positionId: Long): LiveData<Position> {
        return positionDao.findById(positionId)
    }
}