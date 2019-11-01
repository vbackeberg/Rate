package com.example.myapplication.repositories

import android.util.Log
import javax.inject.Singleton

@Singleton
class ApplicantRepository {
    private var applicantCache: HashMap<String, Int> = HashMap()

    fun getCompetency(applicantId: String): Int? {
        Log.d("Applicant ApplicantRepository get", applicantCache[applicantId].toString())
        return applicantCache[applicantId]
    }

    fun updateCompetency(applicantId: String, value: Int) {
        applicantCache[applicantId] = value
        Log.d("Applicant ApplicantRepository update", applicantCache[applicantId].toString())
    }
}