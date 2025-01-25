package fr.uha.wetterwald.summercamp.database

class DbInitializer(private val db: AppDatabase) {

    suspend fun populate() {
//        val personIds = feedPersons()
//        val supervisorIds = feedSupervisors(personIds)
//        val childIds = feedChildren(personIds)
//        feedActivities(supervisorIds, childIds)
    }

    fun clear() {
        db.clearAllTables()
    }

}
