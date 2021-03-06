package com.valerian.rate.services

import android.app.Application
import com.valerian.rate.SingletonHolder
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.CompetencyAreaWithImportance
import com.valerian.rate.entities.CompetencyWithScore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ScoreService private constructor(application: Application) {
    private val database = AppDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val competencyDao = database.competencyDao()
    private val competencyAreaDao = database.competencyAreaDao()

    suspend fun update(applicantId: Long, positionId: Long) {
        val competencyAreasWithImportance = CoroutineScope(Dispatchers.IO)
            .async { competencyAreaDao.findAllByPositionSuspend(positionId) }

        val competenciesWithScore = CoroutineScope(Dispatchers.IO)
            .async { competencyDao.findAllByApplicantSuspend(applicantId) }

        applicantDao.updateScore(
            applicantId,
            calculateScore(competenciesWithScore.await(), competencyAreasWithImportance.await())
        )
    }

    private fun calculateScore(
        competencies: List<CompetencyWithScore>,
        competencyAreas: List<CompetencyAreaWithImportance>
    ): Int {

        return competencyAreas.sumBy { competencyArea ->
            competencyArea.importance.value *
            competencies
                .filter { competency -> competency.competency.competencyAreaId == competencyArea.id }
                .sumBy { competency -> competency.score.value }
        }
    }

    companion object : SingletonHolder<ScoreService, Application>(::ScoreService)
}
