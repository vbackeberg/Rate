package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetencyArea(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    var name: String
) : Id