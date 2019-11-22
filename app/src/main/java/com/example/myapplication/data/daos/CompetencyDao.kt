package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.entities.Competency

@Dao
interface CompetencyDao {
    @Query("SELECT * FROM competency WHERE applicantId = :applicantId")
    fun findAllByApplicant(applicantId: Long): LiveData<List<Competency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(competency: Competency): Long

    @Update
    fun update(competency: Competency)
}