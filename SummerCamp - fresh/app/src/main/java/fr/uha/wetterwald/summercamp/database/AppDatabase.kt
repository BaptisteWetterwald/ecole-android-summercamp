package fr.uha.wetterwald.summercamp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.uha.hassenforder.android.database.DatabaseTypeConverters
import fr.uha.wetterwald.summercamp.model.*

@Database(
    entities = [
        Person::class,
        Supervisor::class,
        Child::class,
        Activity::class,
        ActivityPersonAssociation::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseTypeConverters::class, Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun activityDao(): ActivityDao

    companion object {
        private lateinit var instance : AppDatabase

        fun create (context : Context) : AppDatabase {
            instance = Room.databaseBuilder(context, AppDatabase::class.java, "summer-camp.db")
                .fallbackToDestructiveMigration()
                .build()
            return instance
        }

        fun get() : AppDatabase {
            return instance
        }
    }
}
