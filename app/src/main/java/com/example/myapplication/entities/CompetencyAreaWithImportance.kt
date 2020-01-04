package com.example.myapplication.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CompetencyAreaWithImportance(
    @Embedded val competencyArea: CompetencyArea,
    @Relation(
        parentColumn = "id",
        entityColumn = "competencyAreaId"
    )
    val competencyAreaImportance: CompetencyAreaImportance
) : Id {
    override val id: Long
        get() = competencyArea.id
}