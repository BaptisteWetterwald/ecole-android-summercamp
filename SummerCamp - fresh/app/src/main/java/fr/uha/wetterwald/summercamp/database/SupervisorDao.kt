package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.flow.Flow

@Dao
interface SupervisorDao {

    @Query("SELECT * FROM supervisors")
    fun getAll(): Flow<List<Supervisor>>

    @Query("SELECT * FROM supervisors WHERE supervisorId = :id")
    fun getById(id: Long): Flow<Supervisor?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(supervisor: Supervisor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(supervisor: Supervisor)

    @Delete
    fun delete(supervisor: Supervisor)
}
