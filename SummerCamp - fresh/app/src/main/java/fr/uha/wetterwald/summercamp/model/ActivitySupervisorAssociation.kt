package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity

@Entity(
    tableName = "activity_supervisors",
    primaryKeys = ["supervisorId", "activityId"]
)
data class ActivitySupervisorAssociation(
    val supervisorId: Long,
    val activityId: Long
)