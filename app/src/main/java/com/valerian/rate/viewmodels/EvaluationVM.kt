package com.valerian.rate.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.Applicant

class EvaluationVM(application: Application) : AndroidViewModel(application) {
    private val applicantDao = AppDatabase.getDatabase(application).applicantDao()

    fun getAll(positionId: Long, departmentId: Long): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }
}