package fr.uha.wetterwald.summercamp.database

import java.util.Date

sealed class TeamUpdateDTO {
    data class Name (val tid : Long, val name : String) : TeamUpdateDTO()
    data class StartDay (val tid : Long, val startDay : Date) : TeamUpdateDTO()
    data class Duration (val tid : Long, val duration : Int) : TeamUpdateDTO()
    data class Leader (val tid : Long, val leaderId : Long) : TeamUpdateDTO()
}