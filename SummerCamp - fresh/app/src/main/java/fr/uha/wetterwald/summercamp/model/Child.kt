package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "children",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = ["id"],
        childColumns = ["personId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("personId")]
)
data class Child(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personId: Long, // Référence vers Person
    val parentPhone: String
)
