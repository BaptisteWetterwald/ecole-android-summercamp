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
    fun getById(id: Long): Flow<FullActivity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(activity: Activity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(activity: Activity) : Long

    @Delete
    fun delete(activity: Activity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMember(assoc: ActivityPersonAssociation) : Long

    @Delete
    fun deleteMember(assoc: ActivityPersonAssociation)

    @Query("SELECT * from children WHERE childId IN (SELECT personId FROM activity_person_associations WHERE activityId = :activityId)")
    fun getChildrenForActivity(activityId: Long): Flow<List<Child>>

    @Query("SELECT * from supervisors WHERE supervisorId IN (SELECT personId FROM activity_person_associations WHERE activityId = :activityId)")
    fun getSupervisorsForActivity(activityId: Long): Flow<List<Supervisor>>

    @Query("SELECT * from persons WHERE personId IN (SELECT personId FROM activity_person_associations WHERE activityId = :activityId)")
    fun getMembers(activityId: Long): Flow<List<Person>>

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.Name)

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.Description)

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.MaxParticipants)

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.Location)

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.Period)

    @Update(entity = Activity::class)
    suspend fun update (activity: ActivityUpdateDTO.Specialty)
}
