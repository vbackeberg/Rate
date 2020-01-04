package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetencyArea(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val name: String,
    var importance: Int, //Todo: remove importance from competency area as it is contained in competency area importance.
    val positionId: Long
) : Id