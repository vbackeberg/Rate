package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.databases.ApplicantsDatabase
import com.example.myapplication.repositories.ApplicantsRepository

const val CURRENT_APPLICANT_ID = "current_applicant_id"

class KompetenzVM(application: Application) : AndroidViewModel(application) {
    private val applicantsRepository: ApplicantsRepository

    private val berufserfahrung: MutableLiveData<Int?> = MutableLiveData()
    private val fachkenntnisse: MutableLiveData<Int?> = MutableLiveData()
    private val firmenkenntnisse: MutableLiveData<Int?> = MutableLiveData()
    private val sprachkenntnisse: MutableLiveData<Int?> = MutableLiveData()


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
        if (id != 0L) {
            Log.d("Applicant view model load shared pref id", "$id")
            val applicant = applicantsRepository.get(id)
            berufserfahrung.value = applicant.berufserfahrung
            Log.d("Applicant view model load applicant after", "${berufserfahrung.value}")
        }
        else {
            throw Exception("No applicant Id")
        }
    }
}
