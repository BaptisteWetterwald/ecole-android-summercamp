package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.ActivityChildAssociation

@Dao
interface ActivityChildAssociationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(association: ActivityChildAssociation)

    @Delete
    suspend fun delete(association: ActivityChildAssociation)

    @Query("SELECT childId FROM activity_child_association WHERE activityId = :activityId")
    suspend fun getChildrenForActivity(activityId: Long): List<Long>

    @Query("SELECT activityId FROM activity_child_association WHERE childId = :childId")
    suspend fun getActivitiesForChild(childId: Long): List<Long>
}
