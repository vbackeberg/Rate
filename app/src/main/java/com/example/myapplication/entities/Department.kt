package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String
) : Id {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Department

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}