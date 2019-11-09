package com.example.myapplication.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Applicant

@Dao
interface ApplicantDao {

    @Query("SELECT * FROM applicant WHERE applicantId LIKE :applicantId")
    fun findById(applicantId: Long): Applicant

    @Insert(onConflict = REPLACE)
    fun insert(applicant: Applicant): Long

    @Query("UPDATE applicant SET berufserfahrung = :berufserfahrung WHERE applicantId LIKE :applicantId")
    fun update(applicantId: Long, berufserfahrung: Int)
}
