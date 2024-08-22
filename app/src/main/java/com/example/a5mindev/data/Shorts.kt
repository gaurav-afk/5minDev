package com.example.a5mindev.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shorts(
    @PrimaryKey val title: String,
    val description: String,
    val keyPoints: String,
    val conclusion: String
)
