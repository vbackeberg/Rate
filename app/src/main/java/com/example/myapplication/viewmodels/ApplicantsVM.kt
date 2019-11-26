package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.entities.Applicant
import com.example.myapplication.repositories.ApplicantsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicantsVM(application: Application) : AndroidViewModel(application) {
    private val applicantsRepository = ApplicantsRepository(application)

    fun getAll(): LiveData<List<Applicant>> {
        return applicantsRepository.findAllByPositionAndDepartment(1L, 1L)
    }

    fun new(applicant: Applicant) = CoroutineScope(Dispatchers.IO).launch {
        applicantsRepository.insert(applicant)
    }
}
