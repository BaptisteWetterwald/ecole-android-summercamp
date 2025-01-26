package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity

@Entity(
    tableName = "activity_children",
    primaryKeys = ["childId", "activityId"]
)
data class ActivityChildAssociation(
    val childId: Long,
    val activityId: Long
)