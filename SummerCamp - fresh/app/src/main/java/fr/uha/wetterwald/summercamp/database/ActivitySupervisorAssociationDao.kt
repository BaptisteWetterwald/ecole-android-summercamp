package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.ActivitySupervisorAssociation

@Dao
interface ActivitySupervisorAssociationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(association: ActivitySupervisorAssociation)

    @Delete
    suspend fun delete(association: ActivitySupervisorAssociation)

    @Query("SELECT supervisorId FROM activity_supervisor_association WHERE activityId = :activityId")
    suspend fun getSupervisorsForActivity(activityId: Long): List<Long>

    @Query("SELECT activityId FROM activity_supervisor_association WHERE supervisorId = :supervisorId")
    suspend fun getActivitiesForSupervisor(supervisorId: Long): List<Long>
}
