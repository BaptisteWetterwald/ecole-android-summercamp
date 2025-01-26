package fr.uha.wetterwald.summercamp.model

import androidx.room.TypeConverter
import kotlinx.coroutines.flow.Flow

class Converters {

    companion object {
        fun fromSpecialtyList(specialties: List<Specialty>?): String? {
            return specialties?.joinToString(",") { it.name }
        }

        fun toSpecialtyList(data: String?): List<Specialty> {
            return data?.split(",")?.map { Specialty.valueOf(it) } ?: emptyList()
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
    fun fromSpecialtyList(specialties: List<Specialty>): String {
        return specialties.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toSpecialtyList(data: String): List<Specialty> {
        return data.split(",").map { Specialty.valueOf(it) }
    }

}
