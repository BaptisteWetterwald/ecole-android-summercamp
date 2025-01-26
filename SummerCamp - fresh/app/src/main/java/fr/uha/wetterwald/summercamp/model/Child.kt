package fr.uha.wetterwald.summercamp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "children",
)
data class Child(
    @PrimaryKey(autoGenerate = true)
    val childId: Long = 0,
    var firstname: String,
    var lastname: String,
    var age: Int,
    val gender: Gender,
    val picture: Uri?,
    val parentPhone: String
)
