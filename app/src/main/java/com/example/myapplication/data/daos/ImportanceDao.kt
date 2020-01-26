package com.example.myapplication.data.daos

import androidx.room.*
import com.example.myapplication.entities.Importance
import kotlinx.coroutines.flow.Flow

@Dao
interface ImportanceDao {

    @Query("SELECT * FROM importance WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): Flow<List<Importance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(importances: List<Importance>)

    @Update
    suspend fun update(importance: Importance)
}