package fr.uha.wetterwald.summercamp.model

import androidx.room.Embedded
import androidx.room.Relation

data class FullSupervisor(
    @Embedded
    val person: Person,

    @Relation(parentColumn = "personId", entityColumn = "personRef")
    val supervisor : Supervisor
)