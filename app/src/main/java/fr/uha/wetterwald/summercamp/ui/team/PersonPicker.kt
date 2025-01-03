package fr.uha.wetterwald.summercamp.ui.team

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
import fr.uha.wetterwald.summercamp.database.PersonDao
import fr.uha.wetterwald.summercamp.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PersonPickerViewModel @Inject constructor (private val dao: PersonDao): ViewModel() {
    val persons: Flow<List<Person>> = dao.getAll()
}

@Composable
fun PersonPicker(
    vm : PersonPickerViewModel = hiltViewModel(),
    titleId: Int,
    onSelect: (Person?) -> Unit,
) {
    val persons = vm.persons.collectAsStateWithLifecycle(initialValue = emptyList())
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
                    items = persons.value,
                    key = { person -> person.pid }
                ) {
                   item ->
                    Box (
                        modifier = Modifier.clickable(
                            onClick = { onSelect(item) }
                        )
                    ) {
                        TeamPerson(item)
                    }
                }
            }
        }
    }
}

