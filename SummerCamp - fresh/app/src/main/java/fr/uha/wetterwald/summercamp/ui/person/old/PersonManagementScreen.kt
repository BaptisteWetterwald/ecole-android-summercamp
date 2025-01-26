//package fr.uha.wetterwald.summercamp.ui.person.old
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.CircularProgressIndicator
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import fr.uha.hassenforder.android.viewmodel.Result
//import fr.uha.wetterwald.summercamp.model.FullChild
//import fr.uha.wetterwald.summercamp.model.FullSupervisor
//
//@Composable
//fun PersonManagementScreen(
//    viewModel: PersonViewModel = hiltViewModel(),
//    personId: Long? = null,
//    onPersonSaved: () -> Unit
//) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    LaunchedEffect(personId) {
//        personId?.let { viewModel.edit(it) }
//    }
//
//    when (uiState) {
//        is Result.Loading -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }
//
//        is Result.Error -> {
//            Text(
//                text = "Une erreur est survenue.",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                style = MaterialTheme.typography.body1,
//                color = MaterialTheme.colors.error
//            )
//        }
//
//        is Result.Success -> {
//            val state = (uiState as Result.Success<PersonViewModel.UIState>).content
//
//            PersonForm(
//                person = state.person,
//                child = state.child,
//                supervisor = state.supervisor,
//                onPersonChange = { updatedPerson ->
//                    viewModel.send(PersonViewModel.UIEvent.UpdatePerson(updatedPerson))
//                },
//                onChildChange = { updatedChild ->
//                    viewModel.send(
//                        PersonViewModel.UIEvent.UpdateChild(
//                            updatedChild?.let { FullChild(state.person, it) }
//                        )
//                    )
//                },
//                onSupervisorChange = { updatedSupervisor ->
//                    viewModel.send(
//                        PersonViewModel.UIEvent.UpdateSupervisor(
//                            updatedSupervisor?.let { FullSupervisor(state.person, it) }
//                        )
//                    )
//                },
//                onSave = onPersonSaved
//            )
//        }
//    }
//}
//
//
//
