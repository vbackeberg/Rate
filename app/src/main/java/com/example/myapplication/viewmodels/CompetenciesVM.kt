package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.data.daos.CompetencyDao
import com.example.myapplication.data.databases.CompetenciesDatabase
import com.example.myapplication.entities.Competency

const val CURRENT_APPLICANT_ID = "current_applicant_id"

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val competencyDao: CompetencyDao = CompetenciesDatabase
        .getDatabase(application)
        .competencyDao()

    private val applicantId = getApplication<Application>()
        .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    fun get(): LiveData<List<Competency>> {
        return competencyDao.findAllByApplicant(applicantId)
    }

    fun update(competency: Competency) {
        competencyDao.update(competency)
    }

    fun new(competency: Competency): Long {
        return competencyDao.insert(competency)
    }
}
