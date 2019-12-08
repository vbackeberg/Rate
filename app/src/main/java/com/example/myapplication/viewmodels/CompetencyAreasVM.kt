package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetencyAreasVM(application: Application) : AndroidViewModel(application) {
    private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
        .getDatabase(application)
        .competencyAreaDao()

    fun getAll(positionId: Long): LiveData<List<CompetencyArea>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    fun new(competencyArea: CompetencyArea) = CoroutineScope(Dispatchers.IO).launch {
        competencyAreaDao.insert(competencyArea)
    }
}