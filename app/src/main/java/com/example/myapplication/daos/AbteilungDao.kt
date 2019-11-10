package com.example.myapplication.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.entities.Abteilung

@Dao
interface AbteilungDao {

    @Query("SELECT * FROM abteilung WHERE id LIKE :id")
    fun findById(id: Long): Abteilung

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(abteilung: Abteilung): Long
}