package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.FullActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {

    @Query("SELECT * FROM children")
    fun getAll(): Flow<List<Child>>

    @Query("SELECT * FROM children WHERE childId = :id")
    fun getById(id: Long): Flow<Child?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(child: Child): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(child: Child) : Long

    @Delete
    fun delete(child: Child)
}
