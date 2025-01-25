package fr.uha.wetterwald.summercamp.model

import androidx.room.Embedded
import androidx.room.Relation

data class FullChild(
    @Embedded
    val person: Person,

    @Relation(parentColumn = "personId", entityColumn = "personRef")
    val child : Child
)