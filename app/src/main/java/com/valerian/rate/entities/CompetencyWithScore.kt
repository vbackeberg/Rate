package com.valerian.rate.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CompetencyWithScore(
    @Embedded val score: Score,
    @Relation(
        parentColumn = "competencyId",
        entityColumn = "id"
    )
    val competency: Competency
) : Id {
    override val id: Long
        get() = competency.id
}