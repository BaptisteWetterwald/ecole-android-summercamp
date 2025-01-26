package fr.uha.wetterwald.summercamp.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FullActivity(
    @Embedded
    val activity: Activity,

    @Relation(
        parentColumn = "activityId",
        entityColumn = "childId",
        associateBy = Junction(ActivityChildAssociation::class)
    )
    val children: List<Child>,

    @Relation(
        parentColumn = "activityId",
        entityColumn = "supervisorId",
        associateBy = Junction(ActivitySupervisorAssociation::class)
    )
    val supervisors: List<Supervisor>
)