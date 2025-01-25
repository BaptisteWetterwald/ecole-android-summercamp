package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
import fr.uha.wetterwald.summercamp.model.FullActivity
import fr.uha.wetterwald.summercamp.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM persons")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE personId = :id")
    fun getById(id: Long): Flow<Person?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(person: Person): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(person: Person) : Long

    @Delete
    fun delete(person: Person)
}
