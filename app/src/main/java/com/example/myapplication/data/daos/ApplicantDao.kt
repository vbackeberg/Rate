package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Applicant

@Dao
interface ApplicantDao {

    @Query("SELECT * FROM applicant WHERE (positionId = :positionId AND departmentId = :departmentId)")
    fun findAllByPositionAndDepartment(positionId: Long, departmentId: Long): LiveData<List<Applicant>>

    @Insert(onConflict = REPLACE)
    fun insert(applicant: Applicant)
}
