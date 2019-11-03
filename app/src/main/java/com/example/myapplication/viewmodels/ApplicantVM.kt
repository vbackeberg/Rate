package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.databases.ApplicantsDatabase
import com.example.myapplication.repositories.ApplicantsRepository

class ApplicantVM(application: Application) : AndroidViewModel(application) {
    private val applicantsRepository: ApplicantsRepository

    init {
        val applicantDao = ApplicantsDatabase.getDatabase(application).applicantDao()
        applicantsRepository = ApplicantsRepository(applicantDao)
    }

    private val competency: MutableLiveData<Int> = MutableLiveData()

    fun getCompetency(): LiveData<Int> {
        return competency
    }

    fun updateCompetency(applicantId: Long, newCompetency: Int) {
        competency.postValue(newCompetency)
        applicantsRepository.updateCompetency(applicantId, newCompetency)
    }

    private fun loadCompetency(applicantId: Long) {
        competency.postValue(applicantsRepository.getApplicant(applicantId).competency)
    }

    fun newApplicant(): Long {
        return applicantsRepository.create()
    }
}
