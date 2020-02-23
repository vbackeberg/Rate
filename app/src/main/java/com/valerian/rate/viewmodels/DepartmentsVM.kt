package com.valerian.rate.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.valerian.rate.data.databases.AppDatabase
import com.valerian.rate.entities.Department
import kotlinx.coroutines.launch

class DepartmentsVM(application: Application) : AndroidViewModel(application) {
    private val departmentDao = AppDatabase.getDatabase(application).departmentDao()

    fun getAll(): LiveData<List<Department>> {
        return departmentDao.findAll().asLiveData()
    }

    fun newDepartment(name: String) = viewModelScope.launch {
        departmentDao.insert(Department(0L, name))
    }

    fun update(department: Department) = viewModelScope.launch {
        departmentDao.update(department)
    }

    fun delete(department: Department) = viewModelScope.launch {
        departmentDao.delete(department)
    }
}