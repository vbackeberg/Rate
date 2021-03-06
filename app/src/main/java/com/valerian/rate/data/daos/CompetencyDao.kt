package com.valerian.rate.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.valerian.rate.entities.Competency
import com.valerian.rate.entities.CompetencyWithScore

@Dao
interface CompetencyDao {

    @Transaction
    @Query("SELECT * FROM score JOIN competency ON score.competencyId = competency.id WHERE applicantId = :applicantId AND competencyAreaId = :competencyAreaId")
    fun findAllByApplicantAndCompetencyArea(
        applicantId: Long,
        competencyAreaId: Long
    ): LiveData<List<CompetencyWithScore>>

    @Transaction
    @Query("SELECT * FROM score JOIN competency ON score.competencyId = competency.id WHERE applicantId = :applicantId")
    suspend fun findAllByApplicantSuspend(applicantId: Long): List<CompetencyWithScore>

    @Query("SELECT id FROM applicant")
    fun findAllIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(competencies: List<Competency>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(competency: Competency): Long

    @Delete
    suspend fun delete(competency: Competency)

    @Update
    suspend fun update(competency: Competency)
}