package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Competency(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val applicantId: Long,
    val competencyAreaId: Long,
    val name: String,
    var value: Int
) : Id {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Competency

        if (applicantId != other.applicantId) return false
        if (competencyAreaId != other.competencyAreaId) return false
        if (name != other.name) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = applicantId.hashCode()
        result = 31 * result + competencyAreaId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + value
        return result
    }

}