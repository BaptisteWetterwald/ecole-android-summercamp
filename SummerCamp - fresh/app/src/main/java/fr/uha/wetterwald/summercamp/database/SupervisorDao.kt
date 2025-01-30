package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.flow.Flow

@Dao
interface SupervisorDao {
    @Query("SELECT * from supervisors")
    fun getAll(): Flow<List<Supervisor>>

    @Query("SELECT * from supervisors WHERE supervisorId = :id")
    fun getSupervisorById(id: Long): Flow<Supervisor?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(supervisor: Supervisor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(supervisor: Supervisor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(supervisor: Supervisor): Long

    @Delete
    suspend fun delete(supervisor: Supervisor)
}
