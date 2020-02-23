package com.valerian.rate.entities

import androidx.room.Entity

@Entity(primaryKeys = ["competencyId", "applicantId"])
data class Score(
    val competencyId: Long,
    val applicantId: Long,
    var value: Int
)