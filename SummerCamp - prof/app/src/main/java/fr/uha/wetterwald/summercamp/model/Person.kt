package fr.uha.wetterwald.summercamp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class Person (
    @PrimaryKey(autoGenerate = true)
    val pid : Long,
    val firstname : String,
    val lastname : String,
    val phone : String,
    val gender : Gender,
    val picture : Uri?,
)
