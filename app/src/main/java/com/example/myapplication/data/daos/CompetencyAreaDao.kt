package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entities.CompetencyArea

@Dao
interface CompetencyAreaDao {

    @Query("SELECT * FROM competencyArea WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyArea>>

    @Query("SELECT * FROM competencyArea WHERE positionId = :positionId")
    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyArea>

    @Insert(onConflict = REPLACE)
    suspend fun insert(competencyArea: CompetencyArea)

    @Update
    suspend fun update(competencyArea: CompetencyArea)

    @Query("SELECT * FROM competencyArea WHERE id = :id")
    suspend fun findById(id: Long): CompetencyArea
}