package fr.uha.wetterwald.summercamp.database

import android.net.Uri
import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.Converters
import fr.uha.wetterwald.summercamp.model.FullPerson
import fr.uha.wetterwald.summercamp.model.Gender
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

//    @Query("SELECT * FROM persons")
//    fun getAll(): Flow<List<Person>>
//
//    @Query("SELECT * FROM persons WHERE personId = :id")
//    fun getById(id: Long): Flow<Person?>
//
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    fun create(person: Person): Long
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun update(person: Person) : Long
//
//    @Delete
//    fun delete(person: Person)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person): Long

    @Update
    suspend fun updatePerson(person: Person)

    @Query("DELETE FROM persons WHERE personId = :personId")
    suspend fun deletePerson(personId: Long)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): Flow<List<Person>>

    @Transaction
    @Query("SELECT * FROM persons WHERE personId = :personId")
    fun getFullPersonById(personId: Long): Flow<FullPerson?>

    // Child-specific queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: Child)

    @Query("DELETE FROM children WHERE personRef = :personId")
    suspend fun deleteChildByPersonId(personId: Long)

    // Supervisor-specific queries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupervisor(supervisor: Supervisor)

    @Query("DELETE FROM supervisors WHERE personRef = :personId")
    suspend fun deleteSupervisorByPersonId(personId: Long)

    // Common queries for all persons
    @Query("UPDATE persons SET firstname = :firstname WHERE personId = :personId")
    suspend fun updateFirstname(personId: Long, firstname: String)

    @Query("UPDATE persons SET lastname = :lastname WHERE personId = :personId")
    suspend fun updateLastname(personId: Long, lastname: String)

    @Query("UPDATE persons SET age = :age WHERE personId = :personId")
    suspend fun updateAge(personId: Long, age: Int)

    @Query("UPDATE persons SET gender = :gender WHERE personId = :personId")
    suspend fun updateGender(personId: Long, gender: Gender)

    @Query("UPDATE persons SET picture = :picture WHERE personId = :personId")
    suspend fun updatePicture(personId: Long, picture: Uri?)

    // Child-specific queries
    @Query("UPDATE children SET parentPhone = :parentPhone WHERE personRef = :personId")
    suspend fun updateParentPhone(personId: Long, parentPhone: String)

    // Supervisor-specific queries
    @Query("UPDATE supervisors SET phone = :phone WHERE personRef = :personId")
    suspend fun updatePhone(personId: Long, phone: String)

    @Query("UPDATE supervisors SET specialties = :specialties WHERE personRef = :personId")
    suspend fun updateSpecialties(personId: Long, specialties: List<fr.uha.wetterwald.summercamp.model.Specialty>)

    @Query("UPDATE supervisors SET availability = :availability WHERE personRef = :personId")
    suspend fun updateAvailability(personId: Long, availability: String)
}
