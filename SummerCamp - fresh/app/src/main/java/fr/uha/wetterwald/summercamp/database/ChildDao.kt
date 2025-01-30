package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Child
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    @Query("SELECT * from children")
    fun getAll(): Flow<List<Child>>

    @Query("SELECT * from children WHERE childId = :id")
    fun getChildById(id: Long): Flow<Child?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(child: Child): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(child: Child): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(child: Child): Long

    @Delete
    suspend fun delete(child: Child)
}
