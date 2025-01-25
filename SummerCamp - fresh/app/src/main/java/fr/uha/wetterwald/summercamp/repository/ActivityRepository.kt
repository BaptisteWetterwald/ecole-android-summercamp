package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.ActivityDao
import fr.uha.wetterwald.summercamp.database.ActivityUpdateDTO
import fr.uha.wetterwald.summercamp.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ActivityRepository(
    private val activityDao: ActivityDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAll(): Flow<List<Activity>> {
        return activityDao.getAll()
    }

    fun getActivityById(id: Long): Flow<FullActivity?> {
        return activityDao.getById(id)
    }


    @WorkerThread
    suspend fun create(activity: Activity): Long = withContext(ioDispatcher) {
        return@withContext activityDao.create(activity)
    }

    @WorkerThread
    suspend fun update(update: ActivityUpdateDTO) = withContext(ioDispatcher) {
        when (update) {
            is ActivityUpdateDTO.Name -> activityDao.update(update)
            is ActivityUpdateDTO.Description -> activityDao.update(update)
            is ActivityUpdateDTO.MaxParticipants -> activityDao.update(update)
            is ActivityUpdateDTO.Location -> activityDao.update(update)
            is ActivityUpdateDTO.Period -> activityDao.update(update)
            is ActivityUpdateDTO.Specialty -> activityDao.update(update)
        }
    }

    @WorkerThread
    suspend fun upsert(activity: Activity): Long = withContext(ioDispatcher) {
        return@withContext activityDao.update(activity)
    }

    @WorkerThread
    suspend fun delete(activity: Activity) = withContext(ioDispatcher) {
        activityDao.delete(activity)
    }

    @WorkerThread
    suspend fun addMember(activityId: Long, person: Person) = withContext(ioDispatcher) {
        activityDao.addMember(ActivityPersonAssociation(activityId, person.personId))
    }

    @WorkerThread
    suspend fun removeMember(activityId: Long, person: Person) = withContext(ioDispatcher) {
        activityDao.deleteMember(ActivityPersonAssociation(activityId, person.personId))
    }

    @WorkerThread
    suspend fun getChildrenForActivity(activityId: Long): Flow<List<Child>> = withContext(ioDispatcher) {
        return@withContext activityDao.getChildrenForActivity(activityId)
    }

    @WorkerThread
    suspend fun getSupervisorsForActivity(activityId: Long): Flow<List<Supervisor>> = withContext(ioDispatcher) {
        return@withContext activityDao.getSupervisorsForActivity(activityId)
    }

    @WorkerThread
    suspend fun getMembers(activityId: Long): Flow<List<Person>> = withContext(ioDispatcher) {
        return@withContext activityDao.getMembers(activityId)
    }

}
