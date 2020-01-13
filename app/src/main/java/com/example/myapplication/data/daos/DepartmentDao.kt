package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myapplication.entities.Department

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department")
    fun findAll(): LiveData<List<Department>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(department: Department)

    @Query("SELECT * FROM department WHERE id = :id")
    fun findById(id: Long): LiveData<Department>

    @Update
    suspend fun update(department: Department)

    @Delete
    suspend fun delete(department: Department)
}