package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Department

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department")
    fun findAll(): LiveData<List<Department>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(department: Department)

    @Query("SELECT * FROM department WHERE id = :id")
    suspend fun findById(id: Long): Department
}