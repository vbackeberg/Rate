package com.example.myapplication.repositories

import android.util.Log
import com.example.myapplication.data.daos.ApplicantDao
import com.example.myapplication.entities.Applicant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicantsRepository @Inject constructor(
    private val applicantDao: ApplicantDao
) {
    fun create(): Long {
        return applicantDao.insert(applicant = Applicant(0, null, null, null, null))
    }

    fun get(applicantId: Long): Applicant {
        return applicantDao.findById(applicantId)
    }

    fun updateBerufserfahrung(applicantId: Long, berufserfahrung: Int) {
        applicantDao.update(applicantId, berufserfahrung)
        Log.d("Applicant ApplicantsRepository update", "$applicantId")
    }
}