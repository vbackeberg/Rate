package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.AbteilungDao
import com.example.myapplication.data.databases.AbteilungenDatabase
import com.example.myapplication.entities.Abteilung

class AbteilungenVM(application: Application) : AndroidViewModel(application) {
    private val abteilungDao: AbteilungDao = AbteilungenDatabase
        .getDatabase(application)
        .abteilungDao()

    fun getAbteilungen(): LiveData<List<Abteilung>> {
        return abteilungDao.findAll()
    }

    fun newAbteilung(abteilung: Abteilung): Long {
        return abteilungDao.insert(abteilung)
    }
}