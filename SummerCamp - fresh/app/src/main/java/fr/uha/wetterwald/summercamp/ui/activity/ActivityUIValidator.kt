package fr.uha.wetterwald.summercamp.ui.activity

import fr.uha.wetterwald.summercamp.R
import fr.uha.wetterwald.summercamp.model.FullActivity
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Specialty
import java.time.Instant
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

    fun validatePeriod(newValue: String) : Int? {
        // 25/01/2025 10:00-12:00
        if (newValue.isEmpty()) return R.string.date_must_set
        if (newValue.isBlank()) return R.string.date_must_set
        val parts = newValue.split(" ")
        if (parts.size != 2) return R.string.date_must_set
        val date = parts[0].split("/")
        if (date.size != 3) return R.string.date_must_set
        val time = parts[1].split("-")
        if (time.size != 2) return R.string.date_must_set
        val day = date[0].toIntOrNull() ?: return R.string.day_out_of_range
        val month = date[1].toIntOrNull() ?: return R.string.month_out_of_range
        val year = date[2].toIntOrNull() ?: return R.string.year_out_of_range

        val start = time[0].split(":")
        if (start.size != 2) return R.string.date_must_set
        val hour = start[0].toIntOrNull() ?: return R.string.hour_out_of_range
        val minute = start[1].toIntOrNull() ?: return R.string.minute_out_of_range

        val end = time[1].split(":")
        if (end.size != 2) return R.string.date_must_set
        val hourEnd = end[0].toIntOrNull() ?: return R.string.hour_out_of_range
        val minuteEnd = end[1].toIntOrNull() ?: return R.string.minute_out_of_range

        return when {
            day < 1 ->  R.string.day_out_of_range
            day > 31 ->  R.string.day_out_of_range
            month < 1 ->  R.string.month_out_of_range
            month > 12 ->  R.string.month_out_of_range
            year < 2025 ->  R.string.year_out_of_range
            hour < 0 ->  R.string.hour_out_of_range
            hour > 23 ->  R.string.hour_out_of_range
            minute < 0 ->  R.string.minute_out_of_range
            minute > 59 ->  R.string.minute_out_of_range
            hourEnd < 0 ->  R.string.hour_out_of_range
            hourEnd > 23 ->  R.string.hour_out_of_range
            minuteEnd < 0 ->  R.string.minute_out_of_range
            minuteEnd > 59 ->  R.string.minute_out_of_range
            else -> null
        }
    }

    fun validateActivity () : Boolean {
        if (validateName(this@ActivityUIValidator.activity.activity.name) != null) return false
        if (validateDescription(this@ActivityUIValidator.activity.activity.description) != null) return false
        if (validateMaxParticipants(this@ActivityUIValidator.activity.activity.maxParticipants) != null) return false
        if (validateLocation(this@ActivityUIValidator.activity.activity.location) != null) return false
        if (validatePeriod(this@ActivityUIValidator.activity.activity.period) != null) return false
        if (validateSpecialty(this@ActivityUIValidator.activity.activity.specialty) != null) return false
        if (validateMembers(this@ActivityUIValidator.activity.members) != null) return false
        return true
    }

}