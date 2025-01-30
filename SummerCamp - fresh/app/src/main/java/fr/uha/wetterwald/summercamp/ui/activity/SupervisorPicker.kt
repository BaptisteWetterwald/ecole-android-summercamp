package fr.uha.wetterwald.summercamp.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.hassenforder.android.ui.app.AppTitle
import fr.uha.wetterwald.summercamp.database.SupervisorDao
import fr.uha.wetterwald.summercamp.model.Supervisor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SupervisorPickerViewModel @Inject constructor (private val dao: SupervisorDao): ViewModel() {
    fun getAvailableSupervisors(activityPeriod: String): Flow<List<Supervisor>> {
        val timeRange = extractActivityTimeRange(activityPeriod) ?: return flowOf(emptyList())
        val (activityStart, activityEnd) = timeRange

        return dao.getAll().map { supervisors ->
            supervisors.filter { isSupervisorAvailableForActivity(it, activityStart, activityEnd) }
        }
    }
}

fun isSupervisorAvailableForActivity(supervisor: Supervisor, activityStart: String, activityEnd: String): Boolean {
    val availabilityRegex = Regex("""\d{2}:\d{2}-\d{2}:\d{2}""") // Recherche des créneaux HH:MM-HH:MM
    val availableSlots = availabilityRegex.findAll(supervisor.availability).map { it.value }.toList()

    return availableSlots.any { slot ->
        val (start, end) = slot.split("-")
        activityStart >= start && activityEnd <= end // Vérifie si l'activité est entièrement dans un créneau
    }
}


fun extractActivityTimeRange(period: String): Pair<String, String>? {
    val regex = Regex("""\d{2}:\d{2}-\d{2}:\d{2}""") // Recherche de la période HH:MM-HH:MM
    val match = regex.find(period)?.value ?: return null
    val (start, end) = match.split("-")
    return start to end
}

@Composable
fun SupervisorPicker(
    vm : SupervisorPickerViewModel = hiltViewModel(),
    titleId: Int,
    activityPeriod: String, // Ajout de la période d'activité
    onSelect: (Supervisor?) -> Unit,
) {
    val supervisors = vm.getAvailableSupervisors(activityPeriod).collectAsStateWithLifecycle(initialValue = emptyList())

    Dialog(onDismissRequest = { onSelect(null) }) {
        Scaffold(
            topBar = { TopAppBar(title = { AppTitle(screenTitleId = titleId) }) }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(
                    items = supervisors.value,
                    key = { supervisor -> supervisor.supervisorId }
                ) { item ->
                    Box(modifier = Modifier.clickable { onSelect(item) }) {
                        ActivitySupervisor(item)
                    }
                }
            }
        }
    }
}
