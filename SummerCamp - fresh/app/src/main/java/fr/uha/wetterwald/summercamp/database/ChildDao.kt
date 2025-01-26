package fr.uha.wetterwald.summercamp.database

import androidx.room.*
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
import fr.uha.wetterwald.summercamp.model.FullChild
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(child: Child): Long

    @Update
    suspend fun update(child: Child)

    @Delete
    suspend fun delete(child: Child)

    @Transaction
    @Query("SELECT * FROM persons WHERE personId = :id")
    fun getById(id: Long): Flow<FullChild?>

    @Query("SELECT * FROM children")
    fun getAll(): Flow<List<Child>>

    @Query("""
        SELECT * FROM children 
        WHERE childId IN (SELECT personId FROM activity_person_associations WHERE activityId = :activityId)
    """)
    fun getChildrenForActivity(activityId: Long): Flow<List<Child>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToActivity(association: ActivityPersonAssociation)

    @Delete
    suspend fun removeFromActivity(association: ActivityPersonAssociation)
}
