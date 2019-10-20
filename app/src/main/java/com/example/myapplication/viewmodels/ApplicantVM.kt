package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Applicant

class ApplicantVM : ViewModel() {
    private val applicant: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            loadApplicant()
        }
    }

    fun getApplicant(): LiveData<Int> {
        return applicant
    }

    fun setCompetency(competency: Int) {
//        applicant.value!!.competency = competency
            applicant.value = competency
    }

    private fun loadApplicant() {
        applicant.value = 0
    }
}