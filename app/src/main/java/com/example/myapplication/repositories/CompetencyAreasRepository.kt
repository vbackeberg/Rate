package com.example.myapplication.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyAreaWithImportance

class CompetencyAreasRepository private constructor(application: Application) {
    private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
        .getDatabase(application)
        .competencyAreaDao()

    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyAreaWithImportance>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyAreaWithImportance> {
        return competencyAreaDao.findAllByPositionSuspend(positionId)
    }

    suspend fun update(competencyArea: CompetencyArea) {
        return competencyAreaDao.update(competencyArea)
    }

    suspend fun findById(id: Long): CompetencyArea {
        return competencyAreaDao.findById(id)
    }

    companion object :
        SingletonHolder<CompetencyAreasRepository, Application>(::CompetencyAreasRepository)
}