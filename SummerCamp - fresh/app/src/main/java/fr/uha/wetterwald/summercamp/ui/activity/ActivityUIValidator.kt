package fr.uha.wetterwald.summercamp.ui.activity

import fr.uha.hassenforder.android.ui.field.Time
import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.FullActivity
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Specialty
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class ActivityUIValidator (private val activity : FullActivity) {

    fun validateName(newValue: String) : Int? {
        return when {
            newValue.isEmpty()  ->  R.string.value_empty
            newValue.isBlank()  ->  R.string.value_blank
            newValue.length < 3 ->  R.string.value_too_short
            else -> null
        }
    }

    fun validateDescription(newValue: String) : Int? {
        return when {
            newValue.isEmpty()  ->  R.string.value_empty
            newValue.isBlank()  ->  R.string.value_blank
            newValue.length < 3 ->  R.string.value_too_short
            else -> null
        }
    }

    fun validateMaxParticipants(newValue: Int) : Int? {
        return when {
            newValue < 1 ->  R.string.value_too_short
            newValue > 100 ->  R.string.value_too_long
            else -> null
        }
    }

    fun validateLocation(newValue: String) : Int? {
        return when {
            newValue.isEmpty()  ->  R.string.value_empty
            newValue.isBlank()  ->  R.string.value_blank
            newValue.length < 3 ->  R.string.value_too_short
            else -> null
        }
    }

    fun validateStartTime(newValue: Time) : Int? {
        val min = newValue.minute
        val hour = newValue.hour
        return when {
            min < 0 ->  R.string.value_too_short
            min > 59 ->  R.string.value_too_long
            hour < 8 ->  R.string.value_too_short
            hour > 22 ->  R.string.value_too_long
            else -> null
        }
    }

    fun validateDuration(newValue: Int) : Int? {
        return when {
            newValue < 1 ->  R.string.duration_too_short
            newValue > 8 ->  R.string.duration_too_long
            else -> null
        }
    }

    fun validateSpecialty(newValue: Specialty) : Int? {
        return when {
            newValue.name.isEmpty() ->  R.string.value_empty
            else -> null
        }
    }

    fun validateMembers(newValue: List<Person>) : Int? {
        return when {
            newValue.isEmpty() ->  R.string.value_empty
            else -> null
        }
    }

    private fun instantToMidnight(date: Date): Instant {
        val calendar = GregorianCalendar()
        calendar.time = date
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR, 0)
        return calendar.toInstant()
    }

    fun validateStartDay(newValue: Date?) : Int? {
        if (newValue == null) return R.string.date_must_set
        val day = instantToMidnight(newValue)
        val today = instantToMidnight(Date())
        val between: Long = ChronoUnit.DAYS.between(today, day)
        if (between < 0) return R.string.date_too_old
        if (between >  7) return R.string.date_too_far
        return null
    }

    fun validateActivity () : Boolean {
        if (validateName(this@ActivityUIValidator.activity.activity.name) != null) return false
        if (validateDescription(this@ActivityUIValidator.activity.activity.description) != null) return false
        if (validateMaxParticipants(this@ActivityUIValidator.activity.activity.maxParticipants) != null) return false
        if (validateLocation(this@ActivityUIValidator.activity.activity.location) != null) return false
        if (validateStartDay(this@ActivityUIValidator.activity.activity.startDay) != null) return false
        if (validateStartTime(this@ActivityUIValidator.activity.activity.startTime) != null) return false
        if (validateDuration(this@ActivityUIValidator.activity.activity.duration) != null) return false
        if (validateSpecialties(this@ActivityUIValidator.activity.activity.specialty) != null) return false
        if (validateMembers(this@ActivityUIValidator.activity.members) != null) return false
        return true
    }

}