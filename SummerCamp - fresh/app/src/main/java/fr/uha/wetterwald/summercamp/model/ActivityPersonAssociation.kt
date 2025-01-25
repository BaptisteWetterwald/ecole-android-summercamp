package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity


@Entity(
    tableName = "activity_person_associations",
    primaryKeys = ["personId", "activityId"]
)
data class ActivityPersonAssociation(
    val personId: Long,
    val activityId: Long
)