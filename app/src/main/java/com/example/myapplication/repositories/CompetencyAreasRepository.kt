package com.example.myapplication.repositories

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea

class CompetencyAreasRepository private  constructor(application: Application){
private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
    .getDatabase(application)
    .competencyAreaDao()

    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyArea> {
        return competencyAreaDao.findAllByPositionSuspend(positionId)
    }

    companion object : SingletonHolder<CompetencyAreasRepository, Application>(::CompetencyAreasRepository)
}