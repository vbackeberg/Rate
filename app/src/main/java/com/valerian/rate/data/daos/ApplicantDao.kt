package com.valerian.rate.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.valerian.rate.entities.Applicant

@Dao
interface ApplicantDao {

    @Transaction
    @Query("SELECT * FROM applicant WHERE (positionId = :positionId AND departmentId = :departmentId)")
    fun findAllByPositionAndDepartment(
        positionId: Long,
        departmentId: Long
    ): LiveData<MutableList<Applicant>>

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
