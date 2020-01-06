package com.example.myapplication.services

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.CompetencyWithScore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ScoreService private constructor(application: Application) {
    private val database = AppDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val competencyDao = database.competencyDao()
    private val competencyAreaDao = database.competencyAreaDao()

    suspend fun update(applicantId: Long, positionId: Long) {
        val importanceByCompetencyArea = CoroutineScope(Dispatchers.IO)
            .async {
                competencyAreaDao
                    .findAllByPositionSuspend(positionId)
                    .associate { Pair(it.id, it.importance.value) }
            }

        val competenciesWithScore = CoroutineScope(Dispatchers.IO)
            .async { competencyDao.findAllByApplicantSuspend(applicantId) }

        applicantDao.updateScore(
            applicantId,
            calculateScore(competenciesWithScore.await(), importanceByCompetencyArea.await())
        )
    }

    private fun calculateScore(
        competencies: List<CompetencyWithScore>,
        importanceByCompetencyArea: Map<Long, Int>
    ): Int {

        return competencies.sumBy { competency ->
            competency.score.value * importanceByCompetencyArea.getValue(competency.competency.competencyAreaId)
        }
    }

    companion object : SingletonHolder<ScoreService, Application>(::ScoreService)
}
