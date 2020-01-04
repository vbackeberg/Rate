package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.CompetencyAreaImportance

@Dao
interface CompetencyAreaImportanceDao {

    @Query("SELECT * FROM competencyAreaImportance WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyAreaImportance>>

    @Transaction
    suspend fun insert(
        competencyArea: CompetencyArea,
        positionIds: List<Long>
    ) {
        val competencyAreaId = insert(competencyArea)
        val competencyAreaImportances = mutableListOf<CompetencyAreaImportance>()
        positionIds.forEach { positionId ->
            competencyAreaImportances
                .add(CompetencyAreaImportance(positionId, competencyAreaId, 0))
        }
        insertMany(competencyAreaImportances)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(competencyArea: CompetencyArea): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(competencyAreaImportances: List<CompetencyAreaImportance>)
}