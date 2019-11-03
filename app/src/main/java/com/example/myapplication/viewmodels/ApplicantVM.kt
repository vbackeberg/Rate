package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.databases.ApplicantsDatabase
import com.example.myapplication.entities.Applicant
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

    fun newApplicant(): Long {
        return applicantsRepository.create()
    }

    fun loadApplicant(applicantId: Long) {
        val applicant: LiveData<Applicant> = applicantsRepository.get(applicantId)
        competency.postValue(applicant.value?.competency)
    }
}
