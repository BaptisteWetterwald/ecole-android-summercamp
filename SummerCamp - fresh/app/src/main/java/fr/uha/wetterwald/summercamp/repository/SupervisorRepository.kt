package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.SupervisorDao
import fr.uha.wetterwald.summercamp.model.Supervisor
import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
import fr.uha.wetterwald.summercamp.model.FullSupervisor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SupervisorRepository(
    private val supervisorDao: SupervisorDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAll(): Flow<List<Supervisor>> = supervisorDao.getAll()

    fun getById(id: Long): Flow<FullSupervisor?> = supervisorDao.getById(id)

    fun getSupervisorsForActivity(activityId: Long): Flow<List<Supervisor>> =
        supervisorDao.getSupervisorsForActivity(activityId)

    @WorkerThread
    suspend fun create(supervisor: Supervisor): Long = withContext(ioDispatcher) {
        supervisorDao.create(supervisor)
    }

    @WorkerThread
    suspend fun update(supervisor: Supervisor) = withContext(ioDispatcher) {
        supervisorDao.update(supervisor)
    }

    @WorkerThread
    suspend fun delete(supervisor: Supervisor) = withContext(ioDispatcher) {
        supervisorDao.delete(supervisor)
    }

    @WorkerThread
    suspend fun addToActivity(activityId: Long, supervisorId: Long) = withContext(ioDispatcher) {
        supervisorDao.addToActivity(ActivityPersonAssociation(activityId, supervisorId))
    }

    @WorkerThread
    suspend fun removeFromActivity(activityId: Long, supervisorId: Long) = withContext(ioDispatcher) {
        supervisorDao.removeFromActivity(ActivityPersonAssociation(activityId, supervisorId))
    }
}
