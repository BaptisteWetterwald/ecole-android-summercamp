package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Activity
import fr.uha.wetterwald.summercamp.model.FullActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("SELECT * FROM activities")
    fun getAll(): Flow<List<Activity>>

    @Query("SELECT * FROM activities WHERE id = :id")
    fun getById(id: Long): Flow<Activity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activity): Long

    @Update
    suspend fun update(activity: Activity)

    @Delete
    suspend fun delete(activity: Activity)
}
