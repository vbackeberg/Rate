package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.CURRENT_POSITION_ID
import com.example.myapplication.entities.Applicant
import com.example.myapplication.repositories.ApplicantsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val applicantsRepository = ApplicantsRepository(application)

    private val departmentId = getApplication<Application>()
    .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
    .getLong(CURRENT_DEPARTMENT_ID, 0L)

    private val positionId = getApplication<Application>()
    .getSharedPreferences(CURRENT_POSITION_ID, MODE_PRIVATE)
    .getLong(CURRENT_POSITION_ID, 0L)

    fun getAll(): LiveData<List<Applicant>> {
        return applicantsRepository.findAllByPositionAndDepartment(positionId, departmentId)
    }

    fun new(applicant: Applicant) = CoroutineScope(Dispatchers.IO).launch {
        applicantsRepository.insert(applicant)
    }
}
