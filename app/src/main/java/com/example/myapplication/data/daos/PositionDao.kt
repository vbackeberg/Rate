package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entities.Position

@Dao
interface PositionDao {

    @Query("SELECT * FROM position")
    fun findAll(): LiveData<List<Position>>

    @Query("SELECT id FROM position")
    fun findAllIds(): List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insert(position: Position): Long

    @Query("SELECT * FROM position WHERE id = :id")
    fun findById(id: Long): LiveData<Position>

    @Update
    suspend fun update(position: Position)
}