package com.valerian.rate.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.valerian.rate.entities.CompetencyArea
import com.valerian.rate.entities.CompetencyAreaWithImportance

@Dao
interface CompetencyAreaDao {

    @Transaction
    @Query("SELECT * FROM competencyArea JOIN importance ON competencyArea.id = importance.competencyAreaId WHERE positionId = :positionId")
    fun findAllByPosition(positionId: Long): LiveData<List<CompetencyAreaWithImportance>>

    @Transaction
    @Query("SELECT * FROM competencyArea JOIN importance ON competencyArea.id = importance.competencyAreaId WHERE positionId = :positionId")
    suspend fun findAllByPositionSuspend(positionId: Long): List<CompetencyAreaWithImportance>

    @Update
    suspend fun update(competencyArea: CompetencyArea)

    @Query("SELECT * FROM competencyArea WHERE id = :id")
    suspend fun findById(id: Long?): CompetencyArea

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(competencyArea: CompetencyArea): Long

    @Query("SELECT id FROM competencyArea")
    fun findAllIds(): List<Long>

    @Delete
    suspend fun delete(competencyArea: CompetencyArea)
}