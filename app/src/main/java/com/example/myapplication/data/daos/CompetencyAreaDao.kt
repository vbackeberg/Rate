package com.example.myapplication.data.daos

import androidx.room.Query
import com.example.myapplication.entities.CompetencyArea

interface CompetencyAreaDao {

    @Query("SELECT * FROM competencyArea WHERE positionId = :positionId")
    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyArea>
}