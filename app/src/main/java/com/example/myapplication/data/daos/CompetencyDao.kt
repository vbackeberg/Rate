package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.Competency

@Dao
interface CompetencyDao {
    @Query("SELECT * FROM competency WHERE applicantId = :applicantId AND competencyAreaId = :competencyAreaId")
    fun findAllByApplicantAndCompetencyArea(
        applicantId: Long,
        competencyAreaId: Long
    ): LiveData<List<Competency>>

    @Query("SELECT * FROM competency WHERE applicantId = :applicantId")
    suspend fun findAllByApplicantSuspend(applicantId: Long): List<Competency>

    @Update
    suspend fun update(competency: Competency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(competencies: List<Competency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(competency: Competency)
}