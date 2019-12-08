package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Position

@Dao
interface PositionDao {

    @Query("SELECT * FROM position")
    fun findAll(): LiveData<List<Position>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(position: Position)
}