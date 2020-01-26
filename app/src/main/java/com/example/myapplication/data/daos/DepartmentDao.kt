package com.example.myapplication.data.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myapplication.entities.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department")
    fun findAll(): Flow<List<Department>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(department: Department)

    @Update
    suspend fun update(department: Department)

    @Delete
    suspend fun delete(department: Department)
}