package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.data.databases.AbteilungenDatabase
import com.example.myapplication.repositories.AbteilungenRepository

const val CURRENT_ABTEILUNG_ID = "current_abteilung_id"

class AbteilungenVM(application: Application) : AndroidViewModel(application) {
    private val abteilungenRepository: AbteilungenRepository

    private val sharedPref: SharedPreferences =
        getApplication<Application>()
            .getSharedPreferences(
                CURRENT_ABTEILUNG_ID,
                Context.MODE_PRIVATE
            )

    init {
        val abteilungDao = AbteilungenDatabase
            .getDatabase(application)
            .abteilungDao()
        abteilungenRepository = AbteilungenRepository(abteilungDao)
        loadAbteilung()
    }

    fun newAbteilung(): Long {
        return abteilungenRepository.create()
    }

    private fun loadAbteilung() {
        val id = sharedPref.getLong(CURRENT_ABTEILUNG_ID, 0L)
        if (id != 0L) {
            val abteilung = abteilungenRepository.get(id)
        }
    }
}