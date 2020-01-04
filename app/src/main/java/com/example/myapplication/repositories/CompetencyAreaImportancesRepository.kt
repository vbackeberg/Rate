package com.example.myapplication.repositories

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.ImportanceDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.CompetencyArea

class CompetencyAreaImportancesRepository private constructor(application: Application) {
    private val importanceDao: ImportanceDao =
        CompetencyAreasDatabase.getDatabase(application).importanceDao()

    suspend fun insert(competencyArea: CompetencyArea, positionIds: List<Long>) {
        return importanceDao.insert(competencyArea, positionIds)
    }

    companion object :
        SingletonHolder<CompetencyAreaImportancesRepository, Application>(::CompetencyAreaImportancesRepository)
}