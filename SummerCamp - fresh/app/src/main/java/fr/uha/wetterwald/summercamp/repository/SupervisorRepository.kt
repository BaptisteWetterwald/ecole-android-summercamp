package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.SupervisorDao
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SupervisorRepository(
    private val supervisorDao: SupervisorDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAll(): Flow<List<Supervisor>> {
        return supervisorDao.getAll()
    }

    fun getSupervisorById(id: Long): Flow<Supervisor?> {
        return supervisorDao.getSupervisorById(id)
    }

    @WorkerThread
    suspend fun create(supervisor: Supervisor): Long = withContext(ioDispatcher) {
        return@withContext supervisorDao.create(supervisor)
    }

    @WorkerThread
    suspend fun update(supervisor: Supervisor): Long = withContext(ioDispatcher) {
        return@withContext supervisorDao.update(supervisor)
    }

    @WorkerThread
    suspend fun upsert(supervisor: Supervisor): Long = withContext(ioDispatcher) {
        return@withContext supervisorDao.update(supervisor)
    }

    @WorkerThread
    suspend fun delete(supervisor: Supervisor) = withContext(ioDispatcher) {
        supervisorDao.delete(supervisor)
    }
}
