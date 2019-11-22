package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Competency(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val applicantId: Long,
    val areaId: Long,
    val name: String,
    var value: Int
)