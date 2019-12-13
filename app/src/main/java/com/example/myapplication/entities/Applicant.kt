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
        return if (other is Applicant) {
            (this.positionId == other.positionId)
            && (this.departmentId == other.departmentId)
            && (this.score == other.score)
        } else false
    }

    override fun hashCode(): Int {
        var result = positionId.hashCode()
        result = 31 * result + departmentId.hashCode()
        result = 31 * result + (score ?: 0)
        return result
    }
}