package com.example.myapplication.repositories

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaImportanceDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea

class CompetencyAreaImportancesRepository private constructor(application: Application) {
    private val competencyAreaImportanceDao: CompetencyAreaImportanceDao =
        CompetencyAreasDatabase.getDatabase(application).competencyAreaImportanceDao()

    suspend fun insert(competencyArea: CompetencyArea, positionIds: List<Long>) {
        return competencyAreaImportanceDao.insert(competencyArea, positionIds)
    }

    companion object :
        SingletonHolder<CompetencyAreaImportancesRepository, Application>(::CompetencyAreaImportancesRepository)
}