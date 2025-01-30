package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Query("SELECT * from activities")
    fun getAll(): Flow<List<Activity>>

    @Transaction
    @Query("SELECT * from activities WHERE activityId = :id")
    fun getFullActivityById(id: Long): Flow<FullActivity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(activity: Activity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(activity: Activity): Long

    @Delete
    fun delete(activity: Activity)

    @Query("SELECT * from children WHERE childId IN (SELECT childId FROM activity_children WHERE activityId = :activityId)")
    fun getChildrenForActivity(activityId: Long): Flow<List<Child>>

    @Query("SELECT * from supervisors WHERE supervisorId IN (SELECT supervisorId FROM activity_supervisors WHERE activityId = :activityId)")
    fun getSupervisorsForActivity(activityId: Long): Flow<List<Supervisor>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSupervisor(assoc: ActivitySupervisorAssociation)

    @Delete
    suspend fun deleteSupervisor(assoc: ActivitySupervisorAssociation)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChild(assoc: ActivityChildAssociation)

    @Delete
    suspend fun deleteChild(assoc: ActivityChildAssociation)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.Name)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.Description)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.MaxParticipants)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.Location)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.Period)

    @Update(entity = Activity::class)
    suspend fun update(activity: ActivityUpdateDTO.Specialty)
}
