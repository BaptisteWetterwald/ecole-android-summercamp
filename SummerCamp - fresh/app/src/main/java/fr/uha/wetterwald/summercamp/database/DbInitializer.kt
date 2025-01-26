package fr.uha.wetterwald.summercamp.database

import fr.uha.hassenforder.android.ui.field.Time
import fr.uha.wetterwald.summercamp.model.*
import java.util.*

class DbInitializer(private val db: AppDatabase) {

    // Insérer des données fictives dans la base
    suspend fun populate() {
        clearDatabase() // Optionnel : vider la base avant d'insérer de nouvelles données
    }

    // Vider toutes les tables
    fun clearDatabase() {
        db.clearAllTables()
    }


}
