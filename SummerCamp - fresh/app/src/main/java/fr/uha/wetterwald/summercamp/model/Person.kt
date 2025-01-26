package fr.uha.wetterwald.summercamp.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters

@Entity(tableName = "persons")
@TypeConverters(DatabaseTypeConverters::class, Converters::class)
open class Person(
    @PrimaryKey(autoGenerate = true)
    val personId: Long = 0,
    var firstname: String,
    var lastname: String,
    var age: Int,
    val gender: Gender,
    val picture: Uri?
)
