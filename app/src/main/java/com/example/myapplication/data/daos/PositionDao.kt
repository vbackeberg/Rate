package com.example.myapplication.data.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myapplication.entities.Position
import kotlinx.coroutines.flow.Flow

@Dao
interface PositionDao {

    @Query("SELECT * FROM position WHERE departmentId = :departmentId")
    fun findAllByDepartment(departmentId: Long): Flow<List<Position>>

    @Query("SELECT id FROM position")
    fun findAllIds(): List<Long>

    @Insert(onConflict = REPLACE)
    fun insert(position: Position): Long

    @Query("SELECT * FROM position WHERE id = :id")
    suspend fun findById(id: Long): Position

    @Update
    suspend fun update(position: Position)

    @Delete
    suspend fun delete(position: Position)
}