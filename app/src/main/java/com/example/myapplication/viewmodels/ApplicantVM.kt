package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.databases.ApplicantsDatabase
import com.example.myapplication.repositories.ApplicantsRepository

const val CURRENT_APPLICANT_ID = "current_applicant_id"

class ApplicantVM(application: Application) : AndroidViewModel(application) {
    private val applicantsRepository: ApplicantsRepository

    private val competency: MutableLiveData<Int?> = MutableLiveData()

    private val sharedPref: SharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(
                CURRENT_APPLICANT_ID,
                MODE_PRIVATE
            )

    init {
        val applicantDao = ApplicantsDatabase.getDatabase(application)
            .applicantDao()
        applicantsRepository = ApplicantsRepository(applicantDao)
        sharedPref.edit()
            .putLong(CURRENT_APPLICANT_ID, 4L)
            .apply()

        loadApplicant()
    }

    fun getCompetency(): LiveData<Int?> {
        return competency
    }

    fun updateCompetency(applicantId: Long, newCompetency: Int) {
        competency.value = newCompetency
        applicantsRepository.updateCompetency(applicantId, newCompetency)
    }

    fun newApplicant(): Long {
        return applicantsRepository.create()
    }

    private fun loadApplicant() {
//        val applicant: LiveData<Applicant> = applicantsRepository.get(applicantId)
//        competency.postValue(applicant.value?.competency)
//        Log.d("Applicant view model load applicant before", "${competency.value}")
        val id = sharedPref.getLong(CURRENT_APPLICANT_ID, 0L)
        Log.d("Applicant view model load shared pref id", "$id")
//        competency.value = 4
        val applicant = applicantsRepository.get(id)
        competency.value = applicant.value?.competency
        Log.d("Applicant view model load applicant after", "${competency.value}")
    }
}
