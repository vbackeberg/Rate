package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.repositories.CompetencyAreasRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetencyAreasVM(application: Application) : AndroidViewModel(application) {
    private val competencyAreasRepository = CompetencyAreasRepository.getInstance(application)

    fun getAll(positionId: Long): LiveData<List<CompetencyArea>> {
        return competencyAreasRepository.findAllByPosition(positionId)
    }

    fun new(competencyArea: CompetencyArea) = CoroutineScope(Dispatchers.IO).launch {
        competencyAreasRepository.insert(competencyArea)
    }

    fun update(competencyArea: CompetencyArea) = CoroutineScope(Dispatchers.IO).launch {
        competencyAreasRepository.update(competencyArea)
    }
}