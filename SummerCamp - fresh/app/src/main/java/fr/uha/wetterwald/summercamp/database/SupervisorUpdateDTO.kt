package fr.uha.wetterwald.summercamp.database

import android.net.Uri

sealed class SupervisorUpdateDTO {
    data class Firstname(val supervisorId: Long, val firstname: String) : SupervisorUpdateDTO()
    data class Lastname(val supervisorId: Long, val lastname: String) : SupervisorUpdateDTO()
    data class Age(val supervisorId: Long, val age: Int) : SupervisorUpdateDTO()
    data class Gender(val supervisorId: Long, val gender: fr.uha.wetterwald.summercamp.model.Gender) : SupervisorUpdateDTO()
    data class Picture(val supervisorId: Long, val picture: Uri?) : SupervisorUpdateDTO()

    data class Phone(val supervisorId: Long, val phone: String) : SupervisorUpdateDTO()
    data class Specialties(val supervisorId: Long, val specialties: List<ActivityUpdateDTO.Specialty>) : SupervisorUpdateDTO()
    data class Availability(val supervisorId: Long, val availability: String) : SupervisorUpdateDTO()
}