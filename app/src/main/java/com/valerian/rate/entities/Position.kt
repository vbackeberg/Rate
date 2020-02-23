package com.valerian.rate.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Position(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val departmentId: Long,
    var name: String
) : Id