package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.ChildDao
import fr.uha.wetterwald.summercamp.model.Child
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChildRepository(
    private val childDao: ChildDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    fun getAll () : Flow<List<Child>> {
        return childDao.getAll()
    }

    fun getChildById (id : Long) : Flow<Child?> {
        return childDao.getChildById(id)
    }

    @WorkerThread
    suspend fun create(child: Child) : Long = withContext(ioDispatcher){
        return@withContext childDao.create(child)
    }

    @WorkerThread
    suspend fun update(child: Child) : Long = withContext(ioDispatcher){
        return@withContext childDao.update(child)
    }

    @WorkerThread
    suspend fun upsert(child: Child) : Long = withContext(ioDispatcher){
        return@withContext childDao.update(child)
    }

    @WorkerThread
    suspend fun delete(child: Child) = withContext(ioDispatcher){
        childDao.delete(child)
    }
}
