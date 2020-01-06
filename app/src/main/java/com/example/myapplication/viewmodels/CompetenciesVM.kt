package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Competency
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyWithScore
import com.example.myapplication.entities.Score
import com.example.myapplication.services.ScoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val competencyDao = database.competencyDao()
    private val applicantDao = database.applicantDao()
    private val competencyAreaDao = database.competencyAreaDao()
    private val scoreDao = database.scoreDao()

    private val scoreService = ScoreService.getInstance(application)

    private val applicantId = application
        .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    private val competencyAreaId = application
        .getSharedPreferences(CURRENT_COMPETENCY_AREA_ID, MODE_PRIVATE)
        .getLong(CURRENT_COMPETENCY_AREA_ID, 0L)

    private val positionId = application
        .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    fun getAll(): LiveData<List<CompetencyWithScore>> {
        return competencyDao.findAllByApplicantAndCompetencyArea(applicantId, competencyAreaId)
    }

    suspend fun get(): CompetencyArea {
        return competencyAreaDao.findById(competencyAreaId)
    }

    fun update(score: Score) = CoroutineScope(Dispatchers.IO).launch {
        scoreDao.update(score)
        scoreService.update(applicantId, positionId)
    }

    fun newCompetency(name: String) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val competencyId = competencyDao.insert(Competency(0L, competencyAreaId, name))
            val scores = mutableListOf<Score>()
            applicantDao.findAllIds().forEach { applicantId ->
                scores.add(Score(competencyId, applicantId, 0))
            }
            scoreDao.insertMany(scores)
        }
    }
}
