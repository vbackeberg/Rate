package com.example.myapplication.services

import android.app.Application
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.CompetencyAreaDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Competency
import com.example.myapplication.repositories.ApplicantsRepository
import com.example.myapplication.repositories.CompetenciesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ScoreService private constructor(application: Application) {
    private val applicantsRepository = ApplicantsRepository.getInstance(application)
    private var competenciesRepository = CompetenciesRepository(application)
    private val competencyAreaDao: CompetencyAreaDao = CompetencyAreasDatabase
        .getDatabase(application)
        .competencyAreaDao()

    suspend fun update(applicantId: Long, positionId: Long) {
        val importanceByCompetencyArea = CoroutineScope(Dispatchers.IO)
            .async {
                competencyAreaDao
                    .findAllByPositionSuspend(positionId)
                    .associate { Pair(it.id, it.importance.value) }
            }

        val competenciesNew = CoroutineScope(Dispatchers.IO)
            .async { competenciesRepository.findAllByApplicantSuspend(applicantId) }

        applicantsRepository.updateScore(
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
