package com.example.myapplication.data.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.myapplication.entities.Applicant
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicantDao {

    @Transaction
    @Query("SELECT * FROM applicant WHERE (positionId = :positionId AND departmentId = :departmentId)")
    fun findAllByPositionAndDepartment(
        positionId: Long,
        departmentId: Long
    ): Flow<MutableList<Applicant>>

    @Query("SELECT id FROM applicant")
    fun findAllIds(): List<Long>

    @Insert(onConflict = REPLACE)
    fun insert(applicant: Applicant): Long

    @Query("UPDATE applicant SET score = :score WHERE id = :id")
    suspend fun updateScore(id: Long, score: Int)

    @Update
    suspend fun update(applicant: Applicant)

    @Delete
    suspend fun delete(applicant: Applicant)
}
