package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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

    fun getAll(applicantId: Long, competencyAreaId: Long): LiveData<List<CompetencyWithScore>> {
        return competencyDao
            .findAllByApplicantAndCompetencyArea(applicantId, competencyAreaId)
            .asLiveData()
    }

    suspend fun get(competencyAreaId: Long): CompetencyArea {
        return competencyAreaDao.findById(competencyAreaId)
    }

    fun update(score: Score, positionId: Long) = viewModelScope.launch {
        scoreDao.update(score)
        scoreService.update(score.applicantId, positionId)
    }

    fun update(competency: Competency) = viewModelScope.launch {
        competencyDao.update(competency)
    }

    fun newCompetency(name: String, competencyAreaId: Long) =
        CoroutineScope(Dispatchers.IO).launch {
            database.runInTransaction {
                val competencyId = competencyDao.insert(Competency(0L, competencyAreaId, name))
                val scores = mutableListOf<Score>()
                applicantDao.findAllIds().forEach { applicantId ->
                    scores.add(Score(competencyId, applicantId, 0))
                }
                scoreDao.insertMany(scores)
            }
        }

    fun delete(selectedCompetency: Competency) = viewModelScope.launch {
        competencyDao.delete(selectedCompetency)
    }
}
