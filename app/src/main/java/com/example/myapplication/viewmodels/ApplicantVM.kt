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

    private val berufserfahrung: MutableLiveData<Int?> = MutableLiveData()

    private val sharedPref: SharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(
                CURRENT_APPLICANT_ID,
                MODE_PRIVATE
            )

    init {
        val applicantDao = ApplicantsDatabase
            .getDatabase(application)
            .applicantDao()
        applicantsRepository = ApplicantsRepository(applicantDao)
        loadApplicant()
    }

    fun getBerufserfahrung(): LiveData<Int?> {
        return berufserfahrung
    }

    fun updateBerufserfahrung(applicantId: Long, newBerufserfahrung: Int) {
        berufserfahrung.value = newBerufserfahrung
        applicantsRepository.updateBerufserfahrung(applicantId, newBerufserfahrung)
    }

    fun newApplicant(): Long {
        return applicantsRepository.create()
    }

    private fun loadApplicant() {
        val id = sharedPref.getLong(CURRENT_APPLICANT_ID, 0L)
        Log.d("Applicant view model load shared pref id", "$id")
        val applicant = applicantsRepository.get(id)
        if (applicant != null) {
            berufserfahrung.value = applicant.berufserfahrung
        }
        Log.d("Applicant view model load applicant after", "${berufserfahrung.value}")
    }
}
