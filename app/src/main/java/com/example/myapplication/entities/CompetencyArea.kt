package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetencyArea(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String,
    val importance: Int,
    val positionId: Long
) : Id {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompetencyArea

        if (name != other.name) return false
        if (importance != other.importance) return false
        if (positionId != other.positionId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + importance
        result = 31 * result + positionId.hashCode()
        return result
    }
}