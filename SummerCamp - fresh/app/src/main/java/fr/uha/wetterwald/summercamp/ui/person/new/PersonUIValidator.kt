package fr.uha.wetterwald.summercamp.ui.person.new

import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.FullPerson
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Specialty

class PersonUIValidator(private val fullPerson: FullPerson) {

    fun validateFirstname(newValue: String): Int? {
        return when {
            newValue.isEmpty() -> R.string.value_empty
            newValue.isBlank() -> R.string.value_blank
            newValue.length < 2 -> R.string.value_too_short
            else -> null
        }
    }

    fun validateLastname(newValue: String): Int? {
        return when {
            newValue.isEmpty() -> R.string.value_empty
            newValue.isBlank() -> R.string.value_blank
            newValue.length < 2 -> R.string.value_too_short
            else -> null
        }
    }

    fun validateAge(newValue: Int): Int? {
        return when {
            newValue < 0 -> R.string.value_too_short
            newValue > 120 -> R.string.value_too_long
            else -> null
        }
    }

    fun validateGender(newValue: Gender): Int? {
        return when (newValue) {
            null -> R.string.value_empty
            else -> null
        }
    }

    fun validateParentPhone(newValue: String?): Int? {
        return when {
            newValue == null || newValue.isEmpty() -> R.string.value_empty
            newValue.length != 10 || !newValue.all { it.isDigit() } -> R.string.phone_illegal
            else -> null
        }
    }

    fun validatePhone(newValue: String?): Int? {
        return when {
            newValue == null || newValue.isEmpty() -> R.string.value_empty
            newValue.length != 10 || !newValue.all { it.isDigit() } -> R.string.phone_illegal
            else -> null
        }
    }

    fun validateSpecialties(newValue: List<Specialty>?): Int? {
        return when {
            newValue.isNullOrEmpty() -> R.string.value_empty
            else -> null
        }
    }

    fun validateAvailability(newValue: String?): Int? {
        return when {
            newValue == null || newValue.isEmpty() -> R.string.value_empty
            newValue.length < 5 -> R.string.value_too_short
            else -> null
        }
    }

    fun validatePerson(): Boolean {
        if (validateFirstname(this@PersonUIValidator.fullPerson.person.firstname) != null) return false
        if (validateLastname(this@PersonUIValidator.fullPerson.person.lastname) != null) return false
        if (validateAge(this@PersonUIValidator.fullPerson.person.age) != null) return false
        if (validateGender(this@PersonUIValidator.fullPerson.person.gender) != null) return false

        if (this@PersonUIValidator.fullPerson.child != null) {
            if (validateParentPhone(this@PersonUIValidator.fullPerson.child.parentPhone) != null) return false
        } else {
            if (validatePhone(this@PersonUIValidator.fullPerson.supervisor?.phone) != null) return false
            if (validateSpecialties(this@PersonUIValidator.fullPerson.supervisor?.specialties) != null) return false
            if (validateAvailability(this@PersonUIValidator.fullPerson.supervisor?.availability) != null) return false
        }

        return true
    }
}
