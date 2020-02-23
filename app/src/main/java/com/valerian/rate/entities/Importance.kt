package com.valerian.rate.entities

import androidx.room.Entity

@Entity(primaryKeys = ["positionId", "competencyAreaId"])
data class Importance(
    val positionId: Long,
    val competencyAreaId: Long,
    var value: Int
)