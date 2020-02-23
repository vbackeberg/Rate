package com.valerian.rate.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Applicant(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val positionId: Long,
    val departmentId: Long,
    var name: String,
    val score: Int? = null
) : Id