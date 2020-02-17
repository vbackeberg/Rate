package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.CURRENT_COMPETENCY_AREA_ID
import com.example.myapplication.SELECTED_IDS
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Competency
import com.example.myapplication.entities.Score
import com.example.myapplication.services.ScoreService
import kotlinx.coroutines.CoroutineScope
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

    val applicantId = MutableLiveData<Long>(
        application.getSharedPreferences(SELECTED_IDS, MODE_PRIVATE).getLong(
            CURRENT_APPLICANT_ID,
            0L
        )
    )

    val competencyAreaId = MutableLiveData<Long>(
        application.getSharedPreferences(SELECTED_IDS, MODE_PRIVATE).getLong(
            CURRENT_COMPETENCY_AREA_ID,
            0L
        )
    )

    val competencies = Transformations.switchMap(
        MutableLiveData<List<Long>>(listOfNotNull(applicantId.value, competencyAreaId.value)),
        ::getAll
    )

    val competencyArea = viewModelScope.async { competencyAreaDao.findById(competencyAreaId.value) }

    fun update(score: Score, positionId: Long) = viewModelScope.launch {
        scoreDao.update(score)
        scoreService.update(score.applicantId, positionId)
    }

    fun update(competency: Competency) = viewModelScope.launch {
        competencyDao.update(competency)
    }

    fun newCompetency(name: String) =
        CoroutineScope(Dispatchers.IO).launch {
            database.runInTransaction {
                val competencyId =
                    competencyDao.insert(Competency(0L, competencyAreaId.value!!, name))
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

    private fun getAll(params: List<Long>) =
        competencyDao.findAllByApplicantAndCompetencyArea(params[0], params[1])
}
