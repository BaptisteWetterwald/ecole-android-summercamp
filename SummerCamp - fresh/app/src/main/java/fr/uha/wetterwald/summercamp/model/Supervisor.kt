package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "supervisors",
)
data class Supervisor(
    @PrimaryKey(autoGenerate = true)
    val supervisorId: Long = 0, // Colonne primaire référencée par supervisorId
    val personRef: Long, // Foreign key to Person
    val phone: String,
    val specialties: List<Specialty>,
    val availability: String
)
