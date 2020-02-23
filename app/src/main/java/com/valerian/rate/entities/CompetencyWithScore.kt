package com.valerian.rate.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CompetencyWithScore(
    @Embedded val competency: Competency,
    @Relation(
        parentColumn = "id",
        entityColumn = "competencyId"
    )
    val score: Score
) : Id {
    override val id: Long
        get() = competency.id
}