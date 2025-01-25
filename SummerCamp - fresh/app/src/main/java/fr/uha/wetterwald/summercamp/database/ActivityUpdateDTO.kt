package fr.uha.wetterwald.summercamp.database

import fr.uha.hassenforder.android.ui.field.Time
import java.util.Date

sealed class ActivityUpdateDTO {
    data class Name (val activityId : Long, val name : String) : ActivityUpdateDTO()
    data class Description (val activityId : Long, val description : String) : ActivityUpdateDTO()
    data class MaxParticipants (val activityId : Long, val maxParticipants: Int) : ActivityUpdateDTO()
    data class Location (val activityId : Long, val location : String) : ActivityUpdateDTO()
    data class StartDay (val activityId : Long, val startDay : Date) : ActivityUpdateDTO()
    data class StartTime (val activityId : Long, val startTime : Time) : ActivityUpdateDTO()
    data class Duration (val activityId : Long, val duration : Int) : ActivityUpdateDTO()
    data class Specialty (val activityId: Long, val specialty: fr.uha.wetterwald.summercamp.model.Specialty) : ActivityUpdateDTO()
}