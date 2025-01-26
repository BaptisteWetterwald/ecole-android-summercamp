package fr.uha.wetterwald.summercamp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters

@TypeConverters(DatabaseTypeConverters::class, Converters::class)
@Entity(
    tableName = "supervisors",
)
data class Supervisor(
    @PrimaryKey(autoGenerate = true)
    val supervisorId: Long = 0,
    var firstname: String,
    var lastname: String,
    var age: Int,
    val gender: Gender,
    val picture: Uri?,
    val phone: String,
    val specialties: List<Specialty>,
    val availability: String
)
