package fr.uha.wetterwald.summercamp.database

import android.net.Uri
import fr.uha.wetterwald.summercamp.database.ActivityUpdateDTO.Specialty

sealed class PersonUpdateDTO {
    data class Firstname(val personId: Long, val firstname: String) : PersonUpdateDTO()
    data class Lastname(val personId: Long, val lastname: String) : PersonUpdateDTO()
    data class Age(val personId: Long, val age: Int) : PersonUpdateDTO()
    data class Gender(val personId: Long, val gender: fr.uha.wetterwald.summercamp.model.Gender) : PersonUpdateDTO()
    data class Picture(val personId: Long, val picture: Uri?) : PersonUpdateDTO()
    // Champs spécifiques à Child
    data class ParentPhone(val personId: Long, val parentPhone: String) : PersonUpdateDTO()

    // Champs spécifiques à Supervisor
    data class Phone(val personId: Long, val phone: String) : PersonUpdateDTO()
    data class Specialties(val personId: Long, val specialties: List<Specialty>) : PersonUpdateDTO()
    data class Availability(val personId: Long, val availability: String) : PersonUpdateDTO()
}
