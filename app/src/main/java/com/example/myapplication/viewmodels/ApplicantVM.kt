package com.example.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ApplicantVM : ViewModel() {

    val competency: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}