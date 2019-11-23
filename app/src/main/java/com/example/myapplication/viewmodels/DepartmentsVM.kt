package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.DepartmentDao
import com.example.myapplication.data.databases.DepartmentsDatabase
import com.example.myapplication.entities.Department

class DepartmentsVM(application: Application) : AndroidViewModel(application) {
    private val departmentDao: DepartmentDao = DepartmentsDatabase
        .getDatabase(application)
        .departmentDao()

    fun getAll(): LiveData<List<Department>> {
        return departmentDao.findAll()
    }

    fun new(department: Department) {
        return departmentDao.insert(department)
    }
}