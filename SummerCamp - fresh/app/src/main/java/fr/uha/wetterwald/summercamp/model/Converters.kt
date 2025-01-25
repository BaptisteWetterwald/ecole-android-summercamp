package fr.uha.wetterwald.summercamp.model

import androidx.room.TypeConverter
import fr.uha.hassenforder.android.ui.field.Time

class Converters {

    companion object {
        // Conversion List<Specialty> -> String
        fun fromSpecialtyList(specialties: List<Specialty>?): String? {
            return specialties?.joinToString(",") { it.name }
        }

        // Conversion String -> List<Specialty>
        fun toSpecialtyList(data: String?): List<Specialty> {
            return data?.split(",")?.map { Specialty.valueOf(it) } ?: emptyList()
        }

        // Conversion List<Gender> -> String
        fun fromGenderList(genders: List<Gender>?): String? {
            return genders?.joinToString(",") { it.name }
        }

        // Conversion String -> List<Gender>
        fun toGenderList(data: String?): List<Gender> {
            return data?.split(",")?.map { Gender.valueOf(it) } ?: emptyList()
        }
    }

    // Room TypeConverter annotations for lists
    @TypeConverter
    fun fromSpecialtyListForRoom(specialties: List<Specialty>?): String? {
        return fromSpecialtyList(specialties)
    }

    @TypeConverter
    fun toSpecialtyListForRoom(data: String?): List<Specialty> {
        return toSpecialtyList(data)
    }

    @TypeConverter
    fun fromGenderListForRoom(genders: List<Gender>?): String? {
        return fromGenderList(genders)
    }

    @TypeConverter
    fun toGenderListForRoom(data: String?): List<Gender> {
        return toGenderList(data)
    }

    @TypeConverter
    fun fromTime(time: Time): String {
        return time.let { "${it.hour}:${it.minute}" }
    }

    @TypeConverter
    fun toTime(data: String): Time {
        val parts = data.split(":")
        return Time(hour = parts[0].toInt(), minute = parts[1].toInt())
    }
}
