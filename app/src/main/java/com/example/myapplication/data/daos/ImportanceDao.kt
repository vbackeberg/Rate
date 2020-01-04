package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.CompetencyArea
import com.example.myapplication.entities.Importance

@Dao
interface ImportanceDao {

    @Query("SELECT * FROM importance WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<Importance>>

    @Transaction
    suspend fun insert(
        competencyArea: CompetencyArea,
        positionIds: List<Long>
    ) {
        val competencyAreaId = insert(competencyArea)
        val importances = mutableListOf<Importance>()
        positionIds.forEach { positionId ->
            importances
                .add(Importance(positionId, competencyAreaId, 0))
        }
        insertMany(importances)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(competencyArea: CompetencyArea): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(importances: List<Importance>)

    @Update
    suspend fun update(importance: Importance)
}