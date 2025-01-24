package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM persons")
    fun getAll(): Flow<List<Person>>

    @Query("SELECT * FROM persons WHERE id = :id")
    fun getById(id: Long): Flow<Person?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(person: Person) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(person: Person): Long

    @Delete
    suspend fun delete(person: Person)
}
