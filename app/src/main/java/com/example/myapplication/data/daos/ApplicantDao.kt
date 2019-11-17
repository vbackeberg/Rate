package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.entities.Applicant

@Dao
interface ApplicantDao {

    @Query("SELECT * FROM applicant WHERE id = :id")
    fun findById(id: Long): Applicant

    @Query("SELECT * FROM applicant WHERE (id = :id AND positionId = :positionId AND abteilungId = :abteilungId)")
    fun findAllByPositionAndAbteilung(id: Long, positionId: Long, abteilungId: Long): LiveData<Applicant>

    @Insert(onConflict = REPLACE)
    fun insert(applicant: Applicant): Long
}
