package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.PersonDao
import fr.uha.wetterwald.summercamp.model.Person
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PersonRepository(
    private val dispatcher: CoroutineDispatcher,
    private val personDao : PersonDao
) {

    fun getAll () : Flow<List<Person>> {
        return personDao.getAll()
    }

    fun getPersonById (id : Long) : Flow<Person?> {
        return personDao.getById(id)
    }

    @WorkerThread
    suspend fun create(person: Person) : Long = withContext(dispatcher) {
        return@withContext personDao.insert(person)
    }

    @WorkerThread
    suspend fun update(person: Person) : Long = withContext(dispatcher) {
        return@withContext personDao.update(person)
    }

    @WorkerThread
    suspend fun upsert(person: Person) : Long = withContext(dispatcher) {
        return@withContext personDao.upsert(person)
    }

    @WorkerThread
    suspend fun delete(person: Person) = withContext(dispatcher) {
        personDao.delete(person)
    }

}