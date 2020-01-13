package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myapplication.entities.Position

@Dao
interface PositionDao {

    @Query("SELECT * FROM position WHERE departmentId = :departmentId")
    fun findAllByDepartment(departmentId: Long): LiveData<List<Position>>

    @Query("SELECT id FROM position")
    fun findAllIds(): List<Long>

    @Insert(onConflict = REPLACE)
    fun insert(position: Position): Long

    @Query("SELECT * FROM position WHERE id = :id")
    fun findById(id: Long): LiveData<Position>

    @Update
    suspend fun update(position: Position)

    @Delete
    suspend fun delete(position: Position)
}