package fr.uha.wetterwald.summercamp.ui.supervisor

import android.net.Uri
import fr.uha.hassenforder.android.viewmodel.Result
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.Specialty
import fr.uha.wetterwald.summercamp.ui.person.SupervisorViewModel
import kotlinx.coroutines.flow.StateFlow

class SupervisorUIValidator(private val uiState: StateFlow<Result<SupervisorViewModel.UIState>>) {

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

    @Suppress("SameReturnValue", "unused")
    fun validatePictureChange(newValue: Uri?): Int? {
        return null
    }

}