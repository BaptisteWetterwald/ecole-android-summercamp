package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(
    tableName = "supervisors",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = ["id"],
        childColumns = ["personId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("personId")]
)
@TypeConverters(EnumConverters::class)
data class Supervisor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personId: Long, // Référence vers Person
    val phone: String,
    val specialties: List<Specialty>,
    val availability: String
)
