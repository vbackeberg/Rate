package com.example.myapplication.viewmodels

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.CURRENT_DEPARTMENT_ID
import com.example.myapplication.data.daos.DepartmentDao
import com.example.myapplication.data.databases.DepartmentsDatabase
import com.example.myapplication.entities.Department
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartmentsVM(application: Application) : AndroidViewModel(application) {
    private val departmentDao: DepartmentDao = DepartmentsDatabase
        .getDatabase(application)
        .departmentDao()

    private val departmentId = getApplication<Application>()
        .getSharedPreferences(CURRENT_DEPARTMENT_ID, MODE_PRIVATE)
        .getLong(CURRENT_DEPARTMENT_ID, 0L)

    suspend fun getCurrent(): Department {
        return departmentDao.findById(departmentId)
    }

    fun getAll(): LiveData<List<Department>> {
        return departmentDao.findAll()
    }

    fun new(department: Department) = CoroutineScope(Dispatchers.IO).launch {
        departmentDao.insert(department)
    }
}