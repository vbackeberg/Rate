package com.example.myapplication.data.daos

import androidx.room.*
import com.example.myapplication.entities.Competency
import com.example.myapplication.entities.CompetencyWithScore
import kotlinx.coroutines.flow.Flow

@Dao
interface CompetencyDao {

    @Transaction
    @Query("SELECT * FROM competency, score WHERE competency.id = score.competencyId AND applicantId = :applicantId AND competencyAreaId = :competencyAreaId")
    fun findAllByApplicantAndCompetencyArea(
        applicantId: Long,
        competencyAreaId: Long
    ): Flow<List<CompetencyWithScore>>

    @Transaction
    @Query("SELECT * FROM competency JOIN score ON competency.id = score.competencyId WHERE applicantId = :applicantId")
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