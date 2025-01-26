package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.Index


@Entity(
    tableName = "activity_person_associations",
    primaryKeys = ["personId", "activityId"],
    indices = [
        Index("personId"),
        Index("activityId")
    ]
)
data class ActivityPersonAssociation(
    val personId: Long,
    val activityId: Long
)