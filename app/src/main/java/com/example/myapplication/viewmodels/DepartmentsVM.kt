package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.data.daos.DepartmentDao
import com.example.myapplication.data.databases.CompetencyAreasDatabase
import com.example.myapplication.entities.Department
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartmentsVM(application: Application) : AndroidViewModel(application) {
    private val departmentDao: DepartmentDao = CompetencyAreasDatabase
        .getDatabase(application)
        .departmentDao()

    fun get(id: Long): LiveData<Department> {
        return departmentDao.findById(id)
    }

    fun getAll(): LiveData<List<Department>> {
        return departmentDao.findAll()
    }

    fun newDepartment(name: String) = CoroutineScope(Dispatchers.IO).launch {
        departmentDao.insert(Department(0L, name))
    }

    fun update(department: Department) = CoroutineScope(Dispatchers.IO).launch {
        departmentDao.update(department)
    }
}