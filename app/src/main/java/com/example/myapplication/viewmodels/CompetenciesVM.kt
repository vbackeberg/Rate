package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.data.daos.CompetencyDao
import com.example.myapplication.data.databases.CompetenciesDatabase
import com.example.myapplication.entities.Competency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val competencyDao: CompetencyDao = CompetenciesDatabase
        .getDatabase(application)
        .competencyDao()

    private val applicantId = getApplication<Application>()
        .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    fun getAll(): LiveData<List<Competency>> {
        return competencyDao.findAllByApplicant(applicantId)
    }

    fun update(competency: Competency) = CoroutineScope(Dispatchers.Default).launch {
        competencyDao.update(competency)
    }

    fun new(competency: Competency) = CoroutineScope(Dispatchers.Default).launch {
        competencyDao.insert(competency)
    }
}
