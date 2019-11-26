package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Applicant(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val positionId: Long,
    val departmentId: Long,
    val score: Int? = null
) {
    fun contentEquals(other: Applicant): Boolean {
        return (this.positionId == other.positionId)
            && (this.departmentId == other.departmentId)
            && (this.score == other.score)
    }
}