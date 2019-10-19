package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Applicant

class ApplicantVM : ViewModel() {
    private val applicant: MutableLiveData<Applicant> by lazy {
        MutableLiveData<Applicant>().also {
            loadApplicant()
        }
    }

    fun getApplicant(): LiveData<Applicant> {
        return applicant
    }

    private fun loadApplicant() {
        applicant.value = Applicant("")

    }
}