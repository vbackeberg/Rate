package com.example.myapplication.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.SingletonHolder
import com.example.myapplication.data.daos.ApplicantDao
import com.example.myapplication.data.databases.ApplicantsDatabase
import com.example.myapplication.entities.Applicant

class ApplicantsRepository private constructor(application: Application) {
    private val applicantDao: ApplicantDao = ApplicantsDatabase
        .getDatabase(application)
        .applicantDao()

    fun findAllByPositionAndDepartment(positionId: Long, departmentId: Long): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }

    suspend fun insert(applicant: Applicant) {
        return applicantDao.insert(applicant)
    }

    suspend fun updateScore(id: Long, score: Int) {
        return applicantDao.updateScore(id, score)
    }

    companion object : SingletonHolder<ApplicantsRepository, Application>(::ApplicantsRepository)
}
