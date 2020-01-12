package com.example.myapplication.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.myapplication.entities.Score

@Dao
interface ScoreDao {

    @Update
    suspend fun update(score: Score)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(scores: List<Score>)
}