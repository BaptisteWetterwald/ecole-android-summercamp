package fr.uha.wetterwald.summercamp.model

import android.util.Log
import androidx.room.TypeConverter

class Converters {

    companion object {
        fun fromSpecialtyList(specialties: List<Specialty>?): String? {
            return specialties?.joinToString(",") { it.name }
        }

        fun toSpecialtyList(data: String?): List<Specialty> {
            return data?.split(",")?.map { Specialty.valueOf(it) } ?: emptyList()
        }
    }

    @TypeConverter
    fun fromSpecialtyListForRoom(specialties: List<Specialty>?): String? {
        return fromSpecialtyList(specialties)
    }

    @TypeConverter
    fun toSpecialtyListForRoom(data: String?): List<Specialty> {
        return toSpecialtyList(data)
    }

    @TypeConverter
    fun fromSpecialtyList(specialties: List<Specialty>): String {
        return specialties.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toSpecialtyList(data: String): List<Specialty> {
        if (data.isEmpty()) {
            return emptyList()
        }
        val split = data.split(",")
        if (split.isEmpty()) {
            return emptyList()
        }
        return split.map { Specialty.valueOf(it) }
    }

}
