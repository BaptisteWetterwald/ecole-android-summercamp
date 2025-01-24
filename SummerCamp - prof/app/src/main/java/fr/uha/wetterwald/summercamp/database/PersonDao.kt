package fr.uha.wetterwald.summercamp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.uha.wetterwald.summercamp.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * from persons")
    fun getAll () : Flow<List<Person>>

    @Query("SELECT * from persons WHERE pid = :id")
    fun getPersonById (id : Long) : Flow<Person?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create (person: Person) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update (person: Person) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (person: Person) : Long

    @Delete
    suspend fun delete (person: Person)

}