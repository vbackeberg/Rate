package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.daos.AbteilungDao
import com.example.myapplication.data.databases.AbteilungenDatabase
import com.example.myapplication.entities.Abteilung

class AbteilungenVM(application: Application) : AndroidViewModel(application) {
//    private val abteilungenRepository: AbteilungenRepository
    private val abteilungDao: AbteilungDao
    private val abteilungen: MutableLiveData<List<Abteilung>> = MutableLiveData()

    init {
        abteilungDao = AbteilungenDatabase
            .getDatabase(application)
            .abteilungDao()
    }

    fun getAbteilungen(): LiveData<List<Abteilung>> {
        return abteilungDao.findAll()
    }

    fun newAbteilung(abteilung: Abteilung): Long {
        return abteilungDao.insert(abteilung)
    }

//    private fun loadAbteilungen() {
//        try {
//            abteilungen.value = abteilungenRepository.findAll()
//            Log.d("abteilungenvm load abteilungen", abteilungen.value.toString())
//        } catch (e: Exception) {
//            Log.d("abteilungenvm load abteilungen", "failure")
//        }
//    }
}