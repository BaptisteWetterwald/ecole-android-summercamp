package fr.uha.wetterwald.summercamp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "activity_supervisor_association",
    primaryKeys = ["activityId", "supervisorId"],
    foreignKeys = [
        ForeignKey(
            entity = Activity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Supervisor::class,
            parentColumns = ["id"],
            childColumns = ["supervisorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("activityId"), Index("supervisorId")]
)
data class ActivitySupervisorAssociation(
    val activityId: Long,
    val supervisorId: Long
)
