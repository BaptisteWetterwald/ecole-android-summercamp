package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "children",
)
data class Child(
    @PrimaryKey(autoGenerate = true)
    val childId: Long = 0,
    val personRef : Long, // Foreign key to Person
    val parentPhone: String
)
