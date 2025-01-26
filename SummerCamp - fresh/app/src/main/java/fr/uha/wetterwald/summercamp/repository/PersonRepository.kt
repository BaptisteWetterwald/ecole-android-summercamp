package fr.uha.wetterwald.summercamp.repository

import androidx.annotation.WorkerThread
import fr.uha.wetterwald.summercamp.database.PersonDao
import fr.uha.wetterwald.summercamp.model.Child
import fr.uha.wetterwald.summercamp.model.Person
import fr.uha.wetterwald.summercamp.model.Supervisor
import fr.uha.wetterwald.summercamp.database.PersonUpdateDTO
import fr.uha.wetterwald.summercamp.model.FullPerson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PersonRepository(
    private val personDao: PersonDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun insertPerson(person: Person): Long {
        return personDao.insertPerson(person)
    }

    @WorkerThread
    suspend fun update(update: PersonUpdateDTO) = withContext(ioDispatcher) {
        when (update) {
            is PersonUpdateDTO.Firstname -> personDao.updateFirstname(update.personId, update.firstname)
            is PersonUpdateDTO.Lastname -> personDao.updateLastname(update.personId, update.lastname)
            is PersonUpdateDTO.Age -> personDao.updateAge(update.personId, update.age)
            is PersonUpdateDTO.Gender -> personDao.updateGender(update.personId, update.gender)
            is PersonUpdateDTO.Picture -> personDao.updatePicture(update.personId, update.picture)

            is PersonUpdateDTO.ParentPhone -> personDao.updateParentPhone(update.personId, update.parentPhone)

            is PersonUpdateDTO.Phone -> personDao.updatePhone(update.personId, update.phone)
            //is PersonUpdateDTO.Specialties -> personDao.updateSpecialties(update.personId, update.specialties)
            is PersonUpdateDTO.Availability -> personDao.updateAvailability(update.personId, update.availability)
            else -> throw IllegalArgumentException("Unknown update type")
        }
    }

    fun getFullPersonById(personId: Long): Flow<FullPerson?> {
        return personDao.getFullPersonById(personId)
    }

    suspend fun deletePerson(personId: Long) {
        personDao.deletePerson(personId)
    }

    // Méthode pour insérer un Child
    suspend fun insertChild(child: Child) {
        personDao.insertChild(child)
    }

    // Méthode pour supprimer un Child
    suspend fun deleteChild(personId: Long) {
        personDao.deleteChildByPersonId(personId)
    }

    // Méthode pour insérer un Supervisor
    suspend fun insertSupervisor(supervisor: Supervisor) {
        personDao.insertSupervisor(supervisor)
    }

    // Méthode pour supprimer un Supervisor
    suspend fun deleteSupervisor(personId: Long) {
        personDao.deleteSupervisorByPersonId(personId)
    }

    suspend fun updatePerson(person: Person) {
        personDao.updatePerson(person)
    }

    suspend fun saveChild(child: Child) {
        personDao.insertChild(child)
    }

    suspend fun saveSupervisor(supervisor: Supervisor) {
        personDao.insertSupervisor(supervisor)
    }

    fun getAllPersons(): Flow<List<Person>> {
        return personDao.getAllPersons()
    }
}

//
//class PersonRepository(
//    private val personDao: PersonDao,
//    private val ioDispatcher: CoroutineDispatcher
//) {
//
//    // Récupérer une personne complète
//    fun getFullPersonById(personId: Long): Flow<FullPerson?> {
//        return personDao.getFullPersonById(personId)
//    }
//
//    // Insérer ou mettre à jour une personne
//    suspend fun savePerson(person: Person) {
//        withContext(ioDispatcher) {
//            personDao.insertPerson(person)
//        }
//    }
//
//    // Supprimer une personne
//    suspend fun deletePerson(personId: Long) {
//        withContext(ioDispatcher) {
//            personDao.deletePerson(personId)
//        }
//    }
//
//    // Gestion spécifique pour Child et Supervisor
//    suspend fun saveChild(child: Child) {
//        withContext(ioDispatcher) {
//            personDao.insertChild(child)
//        }
//    }
//
//    suspend fun deleteChild(personId: Long) {
//        withContext(ioDispatcher) {
//            personDao.deleteChildByPersonId(personId)
//        }
//    }
//
//    suspend fun saveSupervisor(supervisor: Supervisor) {
//        withContext(ioDispatcher) {
//            personDao.insertSupervisor(supervisor)
//        }
//    }
//
//    suspend fun deleteSupervisor(personId: Long) {
//        withContext(ioDispatcher) {
//            personDao.deleteSupervisorByPersonId(personId)
//        }
//    }
//
//    @WorkerThread
//    suspend fun update(update: PersonUpdateDTO) = withContext(ioDispatcher) {
//        when (update) {
//            is PersonUpdateDTO.Firstname -> personDao.updateFirstname(update.personId, update.firstname)
//            is PersonUpdateDTO.Lastname -> personDao.updateLastname(update.personId, update.lastname)
//            is PersonUpdateDTO.Age -> personDao.updateAge(update.personId, update.age)
//            is PersonUpdateDTO.Gender -> personDao.updateGender(update.personId, update.gender)
//            is PersonUpdateDTO.Picture -> personDao.updatePicture(update.personId, update.picture)
//            is PersonUpdateDTO.ParentPhone -> personDao.updateParentPhone(update.personId, update.parentPhone)
//            is PersonUpdateDTO.Phone -> personDao.updatePhone(update.personId, update.phone)
//            is PersonUpdateDTO.Specialties -> personDao.updateSpecialties(update.personId, update.specialties)
//            is PersonUpdateDTO.Availability -> personDao.updateAvailability(update.personId, update.availability)
//        }
//    }
//
//}
