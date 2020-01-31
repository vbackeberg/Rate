package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Applicant
import com.example.myapplication.entities.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val competencyDao = database.competencyDao()
    private val scoreDao = database.scoreDao()

    fun getAll(positionId: Long, departmentId: Long): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }

    fun newApplicant(name: String, positionId: Long, departmentId: Long) = CoroutineScope(Dispatchers.IO).launch {
        database.runInTransaction {
            val applicantId = applicantDao.insert(Applicant(0L, positionId, departmentId, name))
            val scores = mutableListOf<Score>()
            competencyDao.findAllIds().forEach { competencyId ->
                scores.add(Score(competencyId, applicantId, 0))
            }
            scoreDao.insertMany(scores)
        }
    }

    fun update(applicant: Applicant) = CoroutineScope(Dispatchers.IO).launch {
        applicantDao.update(applicant)
    }

    fun delete(applicant: Applicant) = CoroutineScope(Dispatchers.IO).launch {
        applicantDao.delete(applicant)
    }
}