package com.valerian.rate.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.valerian.rate.CURRENT_DEPARTMENT_ID
import com.valerian.rate.CURRENT_POSITION_ID
import com.valerian.rate.SELECTED_IDS
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.Applicant
import com.valerian.rate.entities.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val applicantDao = database.applicantDao()
    private val competencyDao = database.competencyDao()
    private val scoreDao = database.scoreDao()

    private val positionId = application
        .getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE)
        .getLong(CURRENT_POSITION_ID, 0L)

    private val departmentId = application
        .getSharedPreferences(SELECTED_IDS, Context.MODE_PRIVATE)
        .getLong(CURRENT_DEPARTMENT_ID, 0L)

    fun getAll(): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }

    fun newApplicant(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            database.runInTransaction {
                val applicantId = applicantDao
                    .insert(Applicant(0L, positionId, departmentId, name))
                val scores = mutableListOf<Score>()
                competencyDao.findAllIds().forEach { competencyId ->
                    scores.add(Score(competencyId, applicantId, 0))
                }
                scoreDao.insertMany(scores)
            }
        }
    }

    fun update(applicant: Applicant) {
        viewModelScope.launch(Dispatchers.IO) { applicantDao.update(applicant) }
    }

    fun delete(applicant: Applicant) {
        viewModelScope.launch(Dispatchers.IO) { applicantDao.delete(applicant) }
    }
}
