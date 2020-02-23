package com.valerian.rate.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    var name: String
) : Id