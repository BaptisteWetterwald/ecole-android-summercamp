package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters

@TypeConverters(DatabaseTypeConverters::class, Converters::class)
@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val activityId: Long = 0,
    val name: String,
    val description: String,
    val maxParticipants: Int,
    val location: String,
    val period: String,
    val specialty: Specialty
)
