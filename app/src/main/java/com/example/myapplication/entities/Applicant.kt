package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Applicant(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val positionId: Long,
    val departmentId: Long,
    val score: Int? = null
) : Id {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Applicant

        if (positionId != other.positionId) return false
        if (departmentId != other.departmentId) return false
        if (score != other.score) return false

        return true
    }

    override fun hashCode(): Int {
        var result = positionId.hashCode()
        result = 31 * result + departmentId.hashCode()
        result = 31 * result + (score ?: 0)
        return result
    }
}