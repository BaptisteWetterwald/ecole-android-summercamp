package fr.uha.wetterwald.summercamp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters

@Entity(tableName = "persons")
@TypeConverters(DatabaseTypeConverters::class, EnumConverters::class)
data class Person(
    @PrimaryKey(autoGenerate = true)
    val personId: Long = 0,
    val firstname: String,
    val lastname: String,
    val age: Int,
    val gender: Gender,
    val picture: Uri?
)
