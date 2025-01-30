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
import javax.inject.Inject

@HiltViewModel
class SupervisorPickerViewModel @Inject constructor (private val dao: SupervisorDao): ViewModel() {
    val supervisors: Flow<List<Supervisor>> = dao.getAll()
}

@Composable
fun SupervisorPicker(
    vm : SupervisorPickerViewModel = hiltViewModel(),
    titleId: Int,
    onSelect: (Supervisor?) -> Unit,
) {
    val supervisors = vm.supervisors.collectAsStateWithLifecycle(initialValue = emptyList())
    Dialog(onDismissRequest = { onSelect(null) }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { AppTitle(screenTitleId = titleId) },
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(
                    items = supervisors.value,
                    key = { supervisor -> supervisor.supervisorId }
                ) {
                        item ->
                    Box (
                        modifier = Modifier.clickable(
                            onClick = { onSelect(item) }
                        )
                    ) {
                        ActivitySupervisor(item)
                    }
                }
            }
        }
    }
}

