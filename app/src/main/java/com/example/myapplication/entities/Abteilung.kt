package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Abteilung(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)