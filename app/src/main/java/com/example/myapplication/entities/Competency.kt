package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Competency(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    val competencyAreaId: Long,
    var name: String
) : Id