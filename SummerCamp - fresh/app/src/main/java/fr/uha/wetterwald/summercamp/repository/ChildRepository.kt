//package fr.uha.wetterwald.summercamp.repository
//
//import androidx.annotation.WorkerThread
//import fr.uha.wetterwald.summercamp.database.ChildDao
//import fr.uha.wetterwald.summercamp.model.Child
//import fr.uha.wetterwald.summercamp.model.ActivityPersonAssociation
//import fr.uha.wetterwald.summercamp.model.FullChild
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.withContext
//
//class ChildRepository(
//    private val childDao: ChildDao,
//    private val ioDispatcher: CoroutineDispatcher
//) {
//
//    fun getAll(): Flow<List<Child>> = childDao.getAll()
//
//    fun getById(id: Long): Flow<FullChild?> = childDao.getById(id)
//
//    fun getChildrenForActivity(activityId: Long): Flow<List<Child>> =
//        childDao.getChildrenForActivity(activityId)
//
//    @WorkerThread
//    suspend fun update(fullChild: FullChild) {
//        childDao.update(fullChild.child)
//    }
//
//    @WorkerThread
//    suspend fun create(child: Child): Long = withContext(ioDispatcher) {
//        childDao.create(child)
//    }
//
//    @WorkerThread
//    suspend fun update(child: Child) = withContext(ioDispatcher) {
//        childDao.update(child)
//    }
//
//    @WorkerThread
//    suspend fun delete(child: Child) = withContext(ioDispatcher) {
//        childDao.delete(child)
//    }
//
//    @WorkerThread
//    suspend fun addToActivity(activityId: Long, childId: Long) = withContext(ioDispatcher) {
//        childDao.addToActivity(ActivityPersonAssociation(activityId, childId))
//    }
//
//    @WorkerThread
//    suspend fun removeFromActivity(activityId: Long, childId: Long) = withContext(ioDispatcher) {
//        childDao.removeFromActivity(ActivityPersonAssociation(activityId, childId))
//    }
//}
