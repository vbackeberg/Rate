package com.example.myapplication.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea

class CompetencyAreasRepository private constructor(application: Application) {
    private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
        .getDatabase(application)
        .competencyAreaDao()

    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyArea>> {
        return competencyAreaDao.findAllByPosition(positionId)
    }

    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyArea> {
        return competencyAreaDao.findAllByPositionSuspend(positionId)
    }

    suspend fun insert(competencyArea: CompetencyArea) {
        return competencyAreaDao.insert(competencyArea)
    }

    suspend fun update(competencyArea: CompetencyArea) {
        return competencyAreaDao.update(competencyArea)
    }

    companion object :
        SingletonHolder<CompetencyAreasRepository, Application>(::CompetencyAreasRepository)
}