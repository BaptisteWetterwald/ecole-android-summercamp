package fr.uha.wetterwald.summercamp.database

import android.net.Uri

sealed class ChildUpdateDTO {
    data class Firstname(val childId: Long, val firstname: String) : ChildUpdateDTO()
    data class Lastname(val childId: Long, val lastname: String) : ChildUpdateDTO()
    data class Age(val childId: Long, val age: Int) : ChildUpdateDTO()
    data class Gender(val childId: Long, val gender: fr.uha.wetterwald.summercamp.model.Gender) : ChildUpdateDTO()
    data class Picture(val childId: Long, val picture: Uri?) : ChildUpdateDTO()

    data class ParentPhone(val childId: Long, val parentPhone: String) : ChildUpdateDTO()
}