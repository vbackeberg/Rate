package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyAreaWithImportance
import com.example.myapplication.entities.Importance
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetencyAreasVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val competencyAreaDao = database.competencyAreaDao()
    private val importanceDao = database.importanceDao()
    private val positionDao = database.positionDao()

    fun getAll(positionId: Long): LiveData<List<CompetencyAreaWithImportance>> {
        return competencyAreaDao.findAllByPosition(positionId).asLiveData()
    }

    suspend fun get(positionId: Long): Position {
        return positionDao.findById(positionId)
    }

    fun newCompetencyArea(name: String) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val competencyAreaId = competencyAreaDao.insert(CompetencyArea(0L, name))
            val importances = mutableListOf<Importance>()
            positionDao.findAllIds().forEach { positionId ->
                importances.add(Importance(positionId, competencyAreaId, 0))
            }
            importanceDao.insertMany(importances)
        }
    }

    fun update(importance: Importance) = viewModelScope.launch {
        importanceDao.update(importance)
    }

    fun update(competencyArea: CompetencyArea) = viewModelScope.launch {
        competencyAreaDao.update(competencyArea)
    }

    fun delete(competencyArea: CompetencyArea) = viewModelScope.launch {
        competencyAreaDao.delete(competencyArea)
    }
}