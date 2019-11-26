package com.example.myapplication.services

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.entities.Competency
import com.example.myapplication.repositories.ApplicantsRepository
import com.example.myapplication.repositories.CompetenciesRepository

class ScoreService(application: Application) {
    private val applicantsRepository = ApplicantsRepository(application)

    private var applicantId = application
        .getSharedPreferences(CURRENT_APPLICANT_ID, AppCompatActivity.MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    private var competenciesRepository = CompetenciesRepository(application)

    suspend fun update(competency: Competency) {
        val competenciesNew = competenciesRepository.findAllByApplicantSuspend(applicantId)
        applicantsRepository.updateScore(competency.applicantId, calculateScore(competenciesNew))
    }

    private fun calculateScore(competencies: List<Competency>): Int {
        return competencies.sumBy { competency -> competency.value * competency.importance }
    }
}
