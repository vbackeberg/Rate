package com.example.myapplication.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.CompetencyDao
import com.example.myapplication.data.databases.CompetenciesDatabase
import com.example.myapplication.entities.Competency

class CompetenciesRepository(application: Application) {
    private val competencyDao: CompetencyDao = CompetenciesDatabase
        .getDatabase(application)
        .competencyDao()

    fun findAllByApplicant(applicantId: Long): LiveData<List<Competency>> {
        return competencyDao.findAllByApplicant(applicantId)
    }

    suspend fun findAllByApplicantSuspend(applicantId: Long): List<Competency> {
        return competencyDao.findAllByApplicantSuspend(applicantId)
    }

    suspend fun update(competency: Competency) {
        return competencyDao.update(competency)
    }

    suspend fun insert(competency: Competency) {
        return competencyDao.insert(competency)
    }
}
