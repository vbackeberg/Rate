package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.daos.ImportanceDao
import com.example.myapplication.data.daos.PositionDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.data.databases.PositionsDatabase
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.Importance
import com.example.myapplication.entities.CompetencyAreaWithImportance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetencyAreasVM(application: Application) : AndroidViewModel(application) {
    private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
        .getDatabase(application)
        .competencyAreaDao()

    private val importanceDao: ImportanceDao = CompetencyAreasDatabase
        .getDatabase(application)
        .importanceDao()

    private val positionDao: PositionDao = PositionsDatabase
        .getDatabase(application)
        .positionDao()

    fun getAll(positionId: Long): LiveData<List<CompetencyAreaWithImportance>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    fun new(competencyArea: CompetencyArea) = CoroutineScope(Dispatchers.IO).launch {
        importanceDao.insert(competencyArea, positionDao.findAllIds())
    }

    fun update(importance: Importance) = CoroutineScope(Dispatchers.IO).launch {
        importanceDao.update(importance)
    }

    suspend fun get(id: Long): CompetencyArea {
        return competencyAreaDao.findById(id)
    }
}