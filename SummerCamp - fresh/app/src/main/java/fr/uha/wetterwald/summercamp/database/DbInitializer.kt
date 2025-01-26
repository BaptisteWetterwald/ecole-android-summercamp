package fr.uha.wetterwald.summercamp.database

import fr.uha.hassenforder.android.ui.field.Time
import fr.uha.wetterwald.summercamp.model.*
import java.util.*

class DbInitializer(private val db: AppDatabase) {

    // Insérer des données fictives dans la base
    suspend fun populate() {
        clearDatabase() // Optionnel : vider la base avant d'insérer de nouvelles données

        val personIds = insertPersons()
        val supervisorIds = insertSupervisors(personIds)
        val childIds = insertChildren(personIds)

        insertActivities(supervisorIds, childIds)
    }

    // Vider toutes les tables
    fun clearDatabase() {
        db.clearAllTables()
    }

    // Ajouter des personnes
    private suspend fun insertPersons(): List<Long> {
        val personDao = db.personDao()
        return listOf(
            personDao.insertPerson(getRandomPerson(Gender.MALE)),
            personDao.insertPerson(getRandomPerson(Gender.FEMALE))
        )
    }


    // Ajouter des superviseurs basés sur des personnes
    private suspend fun insertSupervisors(personIds: List<Long>): List<Long> {
        val supervisorDao = db.supervisorDao()
        return listOf(
            supervisorDao.create(
                Supervisor(
                    personRef = personIds[0],
                    phone = getRandomPhone(),
                    specialties = listOf(Specialty.SPORTS, Specialty.ARTS),
                    availability = "08:00-16:00"
                )
            ),
            supervisorDao.create(
                Supervisor(
                    personRef = personIds[1],
                    phone = getRandomPhone(),
                    specialties = listOf(Specialty.MUSIC, Specialty.CRAFTS),
                    availability = "10:00-18:00"
                )
            )
        )
    }

    // Ajouter des enfants basés sur des personnes
    private suspend fun insertChildren(personIds: List<Long>): List<Long> {
        val childDao = db.childDao()
        return listOf(
            childDao.create(Child(personRef = personIds[2], parentPhone = getRandomPhone())),
            childDao.create(Child(personRef = personIds[3], parentPhone = getRandomPhone()))
        )
    }

    // Ajouter des activités avec des relations
    private suspend fun insertActivities(supervisorIds: List<Long>, childIds: List<Long>) {
        val activityDao = db.activityDao()

        // Insérer une activité
        val activityId = activityDao.create(
            Activity(
                name = "Soccer Match",
                description = "A fun soccer match for all ages",
                maxParticipants = 20,
                location = "Stadium",
                period = "25/01/2025T10:00-12:00",
                specialty = Specialty.SPORTS
            )
        )

        // Associer des superviseurs à l'activité
        supervisorIds.forEach { supervisorId ->
            activityDao.addMember(ActivityPersonAssociation(personId = supervisorId, activityId = activityId))
        }

        // Associer des enfants à l'activité
        childIds.forEach { childId ->
            activityDao.addMember(ActivityPersonAssociation(personId = childId, activityId = activityId))
        }
    }

    // Générer des données fictives
    companion object {
        private val rnd = Random()

        private val maleFirstNames = arrayOf("John", "Alex", "Chris", "Mike")
        private val femaleFirstNames = arrayOf("Sarah", "Emma", "Olivia", "Sophia")
        private val lastNames = arrayOf("Smith", "Johnson", "Williams", "Brown")

        private fun getRandomName(names: Array<String>): String {
            return names[rnd.nextInt(names.size)]
        }

        private fun getRandomFirstName(gender: Gender): String {
            return when (gender) {
                Gender.MALE -> getRandomName(maleFirstNames)
                Gender.FEMALE -> getRandomName(femaleFirstNames)
            }
        }

        private fun getRandomLastName(): String {
            return getRandomName(lastNames)
        }

        private fun getRandomPhone(): String {
            return "0${(1..9).joinToString("") { rnd.nextInt(10).toString() }}"
        }

        private fun getRandomPerson(gender: Gender): Person {
            return Person(
                firstname = getRandomFirstName(gender),
                lastname = getRandomLastName(),
                age = rnd.nextInt(30) + 18,
                gender = gender,
                picture = null
            )
        }
    }
}
