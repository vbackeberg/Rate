package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.repositories.ApplicantRepository
import javax.inject.Inject

class ApplicantVM @Inject constructor(
    private val applicantRepository: ApplicantRepository
) : ViewModel() {

    private val competency: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            loadCompetency()
        }
    }

    fun getCompetency(): LiveData<Int> {
        return competency
    }

    fun setCompetency(value: Int) {
        competency.postValue(value)
        applicantRepository.updateCompetency("1", value)
    }

    private fun loadCompetency() {
        applicantRepository.getCompetency("1")
    }
}
