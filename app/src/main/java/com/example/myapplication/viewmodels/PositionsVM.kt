package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.PositionDao
import com.example.myapplication.data.databases.PositionsDatabase
import com.example.myapplication.entities.Position
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PositionsVM(application: Application) : AndroidViewModel(application) {
    private val positionDao: PositionDao = PositionsDatabase
        .getDatabase(application)
        .positionDao()

    fun getAll(): LiveData<List<Position>> {
        return positionDao.findAll()
    }

    fun new(position: Position) = CoroutineScope(Dispatchers.IO).launch {
        positionDao.insert(position)
    }
}