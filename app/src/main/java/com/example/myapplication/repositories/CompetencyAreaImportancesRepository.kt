package com.example.myapplication.repositories

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaImportanceDao
import com.example.myapplication.data.databases.CompetencyAreaImportancesDatabase
import com.example.myapplication.entities.CompetencyAreaImportance

class CompetencyAreaImportancesRepository private constructor(application: Application) {
    private val competencyAreaImportanceDao: CompetencyAreaImportanceDao =
        CompetencyAreaImportancesDatabase.getDatabase(application).competencyAreaImportanceDao()

    suspend fun insertMany(competencyAreaImportances: List<CompetencyAreaImportance>) {
        return competencyAreaImportanceDao.insertMany(competencyAreaImportances)
    }

    companion object :
        SingletonHolder<CompetencyAreaImportancesRepository, Application>(::CompetencyAreaImportancesRepository)
}