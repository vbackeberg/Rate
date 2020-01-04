package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.entities.CompetencyAreaImportance

@Dao
interface CompetencyAreaImportanceDao {

    @Query("SELECT * FROM competencyAreaImportance WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyAreaImportance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(competencyAreaImportances: List<CompetencyAreaImportance>)
}