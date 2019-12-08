package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetencyArea(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val importance: Int,
    val positionId: Long
)