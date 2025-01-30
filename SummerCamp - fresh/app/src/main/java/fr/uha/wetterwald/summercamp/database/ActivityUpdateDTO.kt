package fr.uha.wetterwald.summercamp.database

sealed class ActivityUpdateDTO {
    data class Name(val activityId: Long, val name: String) : ActivityUpdateDTO()
    data class Description(val activityId: Long, val description: String) : ActivityUpdateDTO()
    data class MaxParticipants(val activityId: Long, val maxParticipants: Int) : ActivityUpdateDTO()
    data class Location(val activityId: Long, val location: String) : ActivityUpdateDTO()
    data class Period(val activityId: Long, val period: String) : ActivityUpdateDTO()
    data class Specialty(
        val activityId: Long,
        val specialty: fr.uha.wetterwald.summercamp.model.Specialty
    ) : ActivityUpdateDTO()
}