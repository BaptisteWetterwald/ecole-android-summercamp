//package fr.uha.wetterwald.summercamp.database
//
//import androidx.room.*
//import fr.uha.wetterwald.summercamp.model.Supervisor
//import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
//import fr.uha.wetterwald.summercamp.model.FullSupervisor
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface SupervisorDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun create(supervisor: Supervisor): Long
//
//    @Update
//    suspend fun update(supervisor: Supervisor)
//
//    @Delete
//    suspend fun delete(supervisor: Supervisor)
//
//    @Transaction
//    @Query("SELECT * FROM persons WHERE personId = :id")
//    fun getById(id: Long): Flow<FullSupervisor?>
//
//    @Query("SELECT * FROM supervisors")
//    fun getAll(): Flow<List<Supervisor>>
//
//    @Query("SELECT * FROM supervisors WHERE supervisorId IN (SELECT personId FROM activity_person_associations WHERE activityId = :activityId)")
//    fun getSupervisorsForActivity(activityId: Long): Flow<List<Supervisor>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addToActivity(association: ActivityPersonAssociation)
//
//    @Delete
//    suspend fun removeFromActivity(association: ActivityPersonAssociation)
//}
