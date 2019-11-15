package com.example.myapplication.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.entities.Abteilung

@Dao
interface AbteilungDao {

    @Query("SELECT * FROM abteilung WHERE id LIKE :id")
    fun find(id: Long): Abteilung

    @Query("SELECT * FROM abteilung")
    fun findAll(): LiveData<List<Abteilung>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(abteilung: Abteilung): Long
}