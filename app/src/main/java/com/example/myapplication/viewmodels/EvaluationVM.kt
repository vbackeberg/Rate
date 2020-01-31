package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.databases.AppDatabase
import com.example.myapplication.entities.Applicant

class EvaluationVM(application: Application) : AndroidViewModel(application) {
    private val applicantDao = AppDatabase.getDatabase(application).applicantDao()

    fun getAll(positionId: Long, departmentId: Long): LiveData<MutableList<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(positionId, departmentId)
    }
}