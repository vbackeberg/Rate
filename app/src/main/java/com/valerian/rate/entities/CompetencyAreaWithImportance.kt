package com.valerian.rate.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CompetencyAreaWithImportance(
    @Embedded val importance: Importance,
    @Relation(
        parentColumn = "competencyAreaId",
        entityColumn = "id"
    )
    val competencyArea: CompetencyArea
) : Id {
    override val id: Long
        get() = competencyArea.id
}