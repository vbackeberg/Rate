package com.valerian.rate.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CompetencyAreaWithImportance(
    @Embedded val competencyArea: CompetencyArea,
    @Relation(
        parentColumn = "id",
        entityColumn = "competencyAreaId"
    )
    val importance: Importance
) : Id {
    override val id: Long
        get() = competencyArea.id
}