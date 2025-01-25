package fr.uha.wetterwald.summercamp.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FullActivity(
    @Embedded
    val activity: Activity,

    @Relation(parentColumn = "activityId", entityColumn = "personId", associateBy = Junction(ActivityPersonAssociation::class))
    val members : List<Person>
)