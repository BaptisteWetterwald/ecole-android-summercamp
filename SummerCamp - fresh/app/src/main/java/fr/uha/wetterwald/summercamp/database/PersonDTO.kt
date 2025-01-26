package fr.uha.wetterwald.summercamp.database

import android.net.Uri
import fr.uha.wetterwald.summercamp.model.Specialty

sealed class PersonDTO {
    data class Firstname(val value: String) : PersonDTO()
    data class Lastname(val value: String) : PersonDTO()
    data class Age(val value: Int) : PersonDTO()
    data class Gender(val value: Gender) : PersonDTO()
    data class Picture(val value: Uri?) : PersonDTO()
    data class IsChild(val value: Boolean) : PersonDTO()
    data class ParentPhone(val value: String) : PersonDTO()
    data class Phone(val value: String) : PersonDTO()
    data class Specialties(val value: List<Specialty>) : PersonDTO()
    data class Availability(val value: String) : PersonDTO()
    object Save : PersonDTO()
    object Delete : PersonDTO()
}
