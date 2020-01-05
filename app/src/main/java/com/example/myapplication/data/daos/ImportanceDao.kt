package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.Importance

@Dao
interface ImportanceDao {

    @Query("SELECT * FROM importance WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<Importance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(importances: List<Importance>)

    @Update
    suspend fun update(importance: Importance)
}