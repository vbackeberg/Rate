package com.example.myapplication.repositories

import com.example.myapplication.data.daos.AbteilungDao
import com.example.myapplication.entities.Abteilung
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AbteilungenRepository @Inject constructor(
    private val abteilungDao: AbteilungDao
){
    fun create(): Long {
        return abteilungDao.insert(abteilung = Abteilung(0, ""))
    }

    fun get(id: Long): Abteilung {
        return abteilungDao.findById(id)
    }
}