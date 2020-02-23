package com.valerian.rate.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.valerian.rate.entities.Score

@Dao
interface ScoreDao {

    @Update
    suspend fun update(score: Score)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(scores: List<Score>)
}