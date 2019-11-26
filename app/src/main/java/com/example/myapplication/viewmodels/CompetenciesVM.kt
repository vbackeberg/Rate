package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_APPLICANT_ID
import com.example.myapplication.entities.Competency
import com.example.myapplication.repositories.CompetenciesRepository
import com.example.myapplication.services.ScoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetenciesVM(application: Application) : AndroidViewModel(application) {
    private val competenciesRepository = CompetenciesRepository(application)
    private val scoreService: ScoreService =
        ScoreService(application)

    private val applicantId = getApplication<Application>()
        .getSharedPreferences(CURRENT_APPLICANT_ID, MODE_PRIVATE)
        .getLong(CURRENT_APPLICANT_ID, 0L)

    fun getAll(): LiveData<List<Competency>> {
        return competenciesRepository.findAllByApplicant(applicantId)
    }

    fun update(competency: Competency) = CoroutineScope(Dispatchers.IO).launch {
        competenciesRepository.update(competency)
        scoreService.update(competency)
    }

    fun new(competency: Competency) = CoroutineScope(Dispatchers.IO).launch {
        competenciesRepository.insert(competency)
    }
}
