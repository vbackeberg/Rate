package com.example.myapplication.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Applicant

@Dao
interface ApplicantDao {

    @Query("SELECT * FROM applicant WHERE applicantId LIKE :applicantId")
    fun findById(applicantId: Long): LiveData<Applicant>

    @Insert(onConflict = REPLACE)
    fun insert(applicant: Applicant): Long

    @Query("UPDATE applicant SET competency = :competency WHERE applicantId LIKE :applicantId")
    fun update(applicantId: Long, competency: Int)
}
