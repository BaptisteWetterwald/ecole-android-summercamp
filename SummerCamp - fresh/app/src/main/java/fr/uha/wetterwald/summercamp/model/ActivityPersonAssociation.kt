package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity


@Entity(
    tableName = "activity_person_associations",
    primaryKeys = ["playlistId", "songId"]
)
data class ActivityPersonAssociation(
    val personId: Long,
    val activityId: Long
)