package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.ApplicantDao
import com.example.myapplication.data.databases.ApplicantsDatabase
import com.example.myapplication.entities.Applicant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val applicantDao: ApplicantDao = ApplicantsDatabase
        .getDatabase(application)
        .applicantDao()

    fun getAll(): LiveData<List<Applicant>> {
        return applicantDao.findAllByPositionAndDepartment(1L, 1L)
    }

    fun new(applicant: Applicant) = CoroutineScope(Dispatchers.Default).launch {
        applicantDao.insert(applicant)
    }
}
