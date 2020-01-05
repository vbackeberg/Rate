package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Competency
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.services.ScoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val database = CompetencyAreasDatabase.getDatabase(application)
    private val competencyDao = database.competencyDao()
    private val applicantDao = database.applicantDao()
    private val competencyAreaDao = database.competencyAreaDao()

    private val scoreService = ScoreService.getInstance(application)

    private val applicantId = getApplication<Application>()
        .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    private val competencyAreaId = getApplication<Application>()
        .getSharedPreferences(CURRENT_COMPETENCY_AREA_ID, MODE_PRIVATE)
        .getLong(CURRENT_COMPETENCY_AREA_ID, 0L)

    private val positionId = getApplication<Application>()
        .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    fun getAll(): LiveData<List<Competency>> {
        return competencyDao.findAllByApplicantAndCompetencyArea(applicantId, competencyAreaId)
    }

    suspend fun getCompetencyArea(): CompetencyArea {
        return competencyAreaDao.findById(competencyAreaId)
    }

    fun update(competency: Competency) = CoroutineScope(Dispatchers.IO).launch {
        competencyDao.update(competency)
        scoreService.update(applicantId, positionId)
    }

    fun new(name: String) = CoroutineScope(Dispatchers.IO).launch {
        val applicantIds: List<Long> = applicantDao.findAllIds()
        val competencies = mutableListOf<Competency>()
        applicantIds.forEach { applicantId ->
            competencies
                .add(Competency(0L, applicantId, competencyAreaId, name, 0))
        } //Todo: Add an applicant competency value entity such as for competency area to facilitate renaming and deletion.

        competencyDao.insertMany(competencies)
    }
}
