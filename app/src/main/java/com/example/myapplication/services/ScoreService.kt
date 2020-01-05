package com.example.myapplication.services

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Competency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ScoreService private constructor(application: Application) {
    private val database = CompetencyAreasDatabase.getDatabase(application)
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

        val competenciesNew = CoroutineScope(Dispatchers.IO)
            .async { competencyDao.findAllByApplicantSuspend(applicantId) }

        applicantDao.updateScore(
            applicantId,
            calculateScore(competenciesNew.await(), importanceByCompetencyArea.await())
        )
    }

    private fun calculateScore(
        competencies: List<Competency>,
        importanceByCompetencyArea: Map<Long, Int>
    ): Int {

        return competencies.sumBy { competency ->
            competency.value * importanceByCompetencyArea.getValue(competency.competencyAreaId)
        }
    }

    companion object : SingletonHolder<ScoreService, Application>(::ScoreService)
}
