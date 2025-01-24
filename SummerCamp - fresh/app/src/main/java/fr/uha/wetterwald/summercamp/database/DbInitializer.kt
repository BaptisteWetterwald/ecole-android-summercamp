package fr.uha.wetterwald.summercamp.database

import fr.uha.wetterwald.summercamp.model.*
import java.util.*

class DbInitializer(private val db: AppDatabase) {

    suspend fun populate() {
        val personIds = feedPersons()
        val supervisorIds = feedSupervisors(personIds)
        val childIds = feedChildren(personIds)
        feedActivities(supervisorIds, childIds)
    }

    fun clear() {
        db.clearAllTables()
    }

    private suspend fun feedPersons(): LongArray {
        val dao = db.personDao()
        val ids = LongArray(6)
        ids[0] = dao.insert(randomPerson(Gender.MALE))
        ids[1] = dao.insert(randomPerson(Gender.FEMALE))
        ids[2] = dao.insert(randomPerson(Gender.MALE))
        ids[3] = dao.insert(randomPerson(Gender.FEMALE))
        ids[4] = dao.insert(randomPerson(Gender.MALE))
        ids[5] = dao.insert(randomPerson(Gender.FEMALE))
        return ids
    }

    private suspend fun feedSupervisors(personIds: LongArray): LongArray {
        val dao = db.supervisorDao()
        val ids = LongArray(2)
        ids[0] = dao.insert(
            Supervisor(
                personId = personIds[0],
                phone = randomPhone(),
                specialties = listOf(Specialty.SPORTS, Specialty.ARTS),
                availability = "08:00-16:00"
            )
        )
        ids[1] = dao.insert(
            Supervisor(
                personId = personIds[1],
                phone = randomPhone(),
                specialties = listOf(Specialty.MUSIC, Specialty.CRAFTS),
                availability = "10:00-18:00"
            )
        )
        return ids
    }

    private suspend fun feedChildren(personIds: LongArray): LongArray {
        val dao = db.childDao()
        val ids = LongArray(3)
        ids[0] = dao.insert(Child(personId = personIds[2], parentPhone = randomPhone()))
        ids[1] = dao.insert(Child(personId = personIds[3], parentPhone = randomPhone()))
        ids[2] = dao.insert(Child(personId = personIds[4], parentPhone = randomPhone()))
        return ids
    }

    private suspend fun feedActivities(supervisorIds: LongArray, childIds: LongArray) {
        val activityDao = db.activityDao()
        val supervisorAssocDao = db.activitySupervisorAssociationDao()
        val childAssocDao = db.activityChildAssociationDao()

        // Création d'une activité
        val activityId = activityDao.insert(
            Activity(
                name = "Football Match",
                description = "A fun football match for all skill levels",
                maxParticipants = 10,
                specialty = Specialty.SPORTS
            )
        )

        // Associer des superviseurs à l'activité
        supervisorAssocDao.insert(ActivitySupervisorAssociation(activityId, supervisorIds[0]))
        supervisorAssocDao.insert(ActivitySupervisorAssociation(activityId, supervisorIds[1]))

        // Associer des enfants à l'activité
        childAssocDao.insert(ActivityChildAssociation(activityId, childIds[0]))
        childAssocDao.insert(ActivityChildAssociation(activityId, childIds[1]))
    }

    companion object {
        private val random = Random()

        private fun randomName(names: Array<String>): String {
            return names[random.nextInt(names.size)]
        }

        private fun randomPhone(): String {
            return "0${(1..9).map { random.nextInt(10) }.joinToString("")}"
        }

        private val maleFirstNames = arrayOf("John", "Alex", "Michael", "Chris", "Daniel")
        private val femaleFirstNames = arrayOf("Sarah", "Emma", "Olivia", "Sophia", "Ava")
        private val lastNames = arrayOf("Smith", "Johnson", "Williams", "Brown", "Jones")

        private fun randomFirstName(gender: Gender): String {
            return when (gender) {
                Gender.MALE -> randomName(maleFirstNames)
                Gender.FEMALE -> randomName(femaleFirstNames)
            }
        }

        private fun randomLastName(): String {
            return randomName(lastNames)
        }

        private fun randomPerson(gender: Gender): Person {
            return Person(
                firstname = randomFirstName(gender),
                lastname = randomLastName(),
                age = random.nextInt(15) + 18,
                gender = gender,
                picture = null // Placeholder pour les images
            )
        }
    }
}
