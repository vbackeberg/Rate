package com.valerian.rate.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.valerian.rate.CURRENT_APPLICANT_ID
import com.valerian.rate.CURRENT_COMPETENCY_AREA_ID
import com.valerian.rate.CURRENT_POSITION_ID
import com.valerian.rate.SELECTED_IDS
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.Competency
import com.valerian.rate.entities.CompetencyWithScore
import com.valerian.rate.entities.Score
import com.valerian.rate.services.ScoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val competencyDao = database.competencyDao()
    private val applicantDao = database.applicantDao()
    private val competencyAreaDao = database.competencyAreaDao()
    private val scoreDao = database.scoreDao()

    private val scoreService = ScoreService.getInstance(application)

    val applicantId = application.getSharedPreferences(SELECTED_IDS, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    val positionId = application.getSharedPreferences(SELECTED_IDS, MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    private val competencyAreaId = application.getSharedPreferences(SELECTED_IDS, MODE_PRIVATE)
        .getLong(CURRENT_COMPETENCY_AREA_ID, 0L)

    val competencyArea = viewModelScope.async(Dispatchers.IO) { competencyAreaDao.findById(competencyAreaId) }

    val competencies = MutableLiveData<List<CompetencyWithScore>>().also {
        viewModelScope.launch(Dispatchers.IO) { loadCompetencies() }
    }

    fun update(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        scoreDao.update(score)
        scoreService.update(score.applicantId, positionId)
        loadCompetencies()
    }

    fun update(competency: Competency) = viewModelScope.launch(Dispatchers.IO) {
        competencyDao.update(competency)
        loadCompetencies()
    }

    fun newCompetency(name: String) = viewModelScope.launch(Dispatchers.IO) {
        database.runInTransaction {
            val competencyId = competencyDao.insert(Competency(0L, competencyAreaId, name))
            val scores = mutableListOf<Score>()
            applicantDao.findAllIds().forEach { applicantId ->
                scores.add(Score(competencyId, applicantId, 0))
            }
            scoreDao.insertMany(scores)
            loadCompetencies()
        }
    }

    fun delete(selectedCompetency: Competency) = viewModelScope.launch(Dispatchers.IO) {
        competencyDao.delete(selectedCompetency)
    }

    private fun loadCompetencies() {
        competencies.postValue(
            competencyDao.findAllByApplicantAndCompetencyArea(applicantId, competencyAreaId)
        )
    }
}

