package com.example.myapplication.repositories

import android.util.Log
import com.example.myapplication.daos.ApplicantDao
import com.example.myapplication.entities.Applicant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicantsRepository @Inject constructor(
    private val applicantDao: ApplicantDao
) {
    fun create(): Long {
        return applicantDao.insert(applicant = Applicant(0, null))
    }

    fun getApplicant(applicantId: Long): Applicant {
        return applicantDao.findById(applicantId)
    }

    fun updateCompetency(applicantId: Long, competency: Int) {
        applicantDao.update(applicantId, competency)
        Log.d("Applicant ApplicantsRepository update", "u")
    }
}