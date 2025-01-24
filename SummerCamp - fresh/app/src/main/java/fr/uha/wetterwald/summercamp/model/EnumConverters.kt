package fr.uha.wetterwald.summercamp.model

import androidx.room.TypeConverter

class EnumConverters {

    // Spécifique pour List<Specialty>
    @TypeConverter
    fun fromSpecialtyList(specialties: List<Specialty>?): String? {
        return specialties?.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toSpecialtyList(data: String?): List<Specialty> {
        return data?.split(",")?.map { Specialty.valueOf(it) } ?: emptyList()
    }

    // Spécifique pour List<Gender>
    @TypeConverter
    fun fromGenderList(genders: List<Gender>?): String? {
        return genders?.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toGenderList(data: String?): List<Gender> {
        return data?.split(",")?.map { Gender.valueOf(it) } ?: emptyList()
    }
}
