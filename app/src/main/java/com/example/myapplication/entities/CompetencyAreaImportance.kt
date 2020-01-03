package com.example.myapplication.entities

import androidx.room.Entity

@Entity(primaryKeys = ["positionId", "competencyAreaId"])
data class CompetencyAreaImportance (
    val positionId: Long,
    val competencyAreaId: Long,
    var importance: Int
)