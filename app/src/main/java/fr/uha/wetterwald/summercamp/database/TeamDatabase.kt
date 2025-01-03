package fr.uha.wetterwald.summercamp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Team
import fr.uha.wetterwald.summercamp.model.TeamPersonAssociation

@TypeConverters(DatabaseTypeConverters::class)
@Database(
    entities = [
        Person::class,
        Team::class,
        TeamPersonAssociation::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TeamDatabase : RoomDatabase() {

    abstract fun personDAO() : PersonDao

    abstract fun teamDAO() : TeamDao

    companion object {
        private lateinit var instance : TeamDatabase

        fun create (context : Context) : TeamDatabase {
            instance = Room.databaseBuilder(context, TeamDatabase::class.java, "team.db").build()
            return instance
        }

        fun get() : TeamDatabase {
            return instance
        }

    }

}
