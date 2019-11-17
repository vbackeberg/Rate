package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Applicant(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @Relation(parentColumn = "id", entityColumn = "applicantId") val competencies: List<Competency>,
    val positionId: Long,
    val abteilungId: Long
)