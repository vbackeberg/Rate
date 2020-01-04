package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyAreaWithImportance

@Dao
interface CompetencyAreaDao {

    @Query("SELECT * FROM competencyArea JOIN competencyAreaImportance ON competencyArea.id = competencyAreaImportance.competencyAreaId WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyAreaWithImportance>>

    @Query("SELECT * FROM competencyArea JOIN competencyAreaImportance ON competencyArea.id = competencyAreaImportance.competencyAreaId WHERE positionId = :positionId")
    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyAreaWithImportance>

    @Update
    suspend fun update(competencyArea: CompetencyArea)

    @Query("SELECT * FROM competencyArea WHERE id = :id")
    suspend fun findById(id: Long): CompetencyArea
}