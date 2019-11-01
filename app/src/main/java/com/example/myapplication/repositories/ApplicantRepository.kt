package com.example.myapplication.repositories

import android.util.Log
import javax.inject.Singleton

@Singleton
class ApplicantRepository {
    private lateinit var applicantCache: HashMap<String, Int>

    fun getCompetency(applicantId: String): Int? {
        Log.d("get", applicantCache[applicantId].toString())
        return applicantCache[applicantId]
    }

    fun updateCompetency(applicantId: String, value: Int) {
        Log.d("update", applicantCache[applicantId].toString())
        applicantCache[applicantId] = value
    }
}