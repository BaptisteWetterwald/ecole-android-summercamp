package fr.uha.wetterwald.summercamp.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FullActivity(
    @Embedded
    val activity: Activity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ActivitySupervisorAssociation::class)
    )
    val supervisors: List<Supervisor>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ActivityChildAssociation::class)
    )
    val children: List<Child>
)
