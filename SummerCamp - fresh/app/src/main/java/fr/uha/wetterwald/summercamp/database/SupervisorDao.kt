package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.flow.Flow

@Dao
interface SupervisorDao {

    @Query("SELECT * FROM supervisors")
    fun getAll(): Flow<List<Supervisor>>

    @Query("SELECT * FROM supervisors WHERE id = :id")
    fun getById(id: Long): Flow<Supervisor?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supervisor: Supervisor): Long

    @Update
    suspend fun update(supervisor: Supervisor)

    @Delete
    suspend fun delete(supervisor: Supervisor)
}
