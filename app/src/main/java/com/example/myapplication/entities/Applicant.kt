package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Applicant(
    @PrimaryKey(autoGenerate = true) val applicantId: Long,
    val berufserfahrung: Int?,
    val fachkenntnisse: Int?,
    val firmenkenntnisse: Int?,
    val sprachkenntnisse: Int?
)