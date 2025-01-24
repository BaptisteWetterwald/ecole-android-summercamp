package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "teams")
data class Team (
    @PrimaryKey(autoGenerate = true)
    val tid : Long,
    val name : String,
    val startDay : Date,
    val duration : Int,
    val leaderId : Long,
)