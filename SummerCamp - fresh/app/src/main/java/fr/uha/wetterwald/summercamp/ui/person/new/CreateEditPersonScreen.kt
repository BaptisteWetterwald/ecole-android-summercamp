//package fr.uha.wetterwald.summercamp.ui.person.new
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.ramcosta.composedestinations.annotation.Destination
//import com.ramcosta.composedestinations.annotation.RootGraph
//import fr.uha.hassenforder.android.ui.StateScreen
//import fr.uha.hassenforder.android.ui.field.OutlinedIntFieldWrapper
//import fr.uha.hassenforder.android.ui.field.OutlinedTextFieldWrapper
//import fr.uha.wetterwald.summercamp.R
//import fr.uha.wetterwald.summercamp.model.Gender
//import fr.uha.wetterwald.summercamp.ui.field.OutlinedSpecialtiesFieldWrapper
//
//@Destination<RootGraph>
//@Composable
//fun CreateEditPersonScreen(
//    vm: PersonViewModel = hiltViewModel(),
//    personId: Long? = null
//) {
//    val uiState by vm.uiState.collectAsStateWithLifecycle()
//
//    LaunchedEffect(personId) {
//        if (personId == null) vm.create() else vm.edit(personId)
//    }
//
//    Scaffold(
//        topBar = { Text(text = if (personId == null) "Create Person" else "Edit Person") }
//    ) { innerPadding ->
//        Column(modifier = Modifier.padding(innerPadding)) {
//            StateScreen(state = uiState) { content ->
//                SuccessPersonScreen(content, { vm.send(it) })
//            }
//        }
//    }
//}
//
//@Composable
//fun SuccessPersonScreen(
//    person: PersonViewModel.UIState,
//    send: (PersonViewModel.UIEvent) -> Unit
//) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        // Firstname
//        OutlinedTextFieldWrapper(
//            field = person.firstname,
//            onValueChange = { send(PersonViewModel.UIEvent.FirstnameChanged(it)) },
//            labelId = R.string.firstname
//        )
//
//        // Lastname
//        OutlinedTextFieldWrapper(
//            field = person.lastname,
//            onValueChange = { send(PersonViewModel.UIEvent.LastnameChanged(it)) },
//            labelId = R.string.lastname
//        )
//
//        // Age
//        OutlinedIntFieldWrapper(
//            field = person.age,
//            onValueChange = { send(PersonViewModel.UIEvent.AgeChanged(it)) },
//            labelId = R.string.age
//        )
//
//        // Gender
//        Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            Text(text = "Gender:")
//            Gender.values().forEach { gender ->
//                Row(
//                    modifier = Modifier.selectable(
//                        selected = person.gender.value == gender,
//                        onClick = { send(PersonViewModel.UIEvent.GenderChanged(gender)) }
//                    ).padding(horizontal = 8.dp)
//                ) {
//                    RadioButton(
//                        selected = person.gender.value == gender,
//                        onClick = { send(PersonViewModel.UIEvent.GenderChanged(gender)) }
//                    )
//                    Text(text = gender.name)
//                }
//            }
//        }
//
//        // Role selection: Child or Supervisor
//        Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            Text(text = "Role:")
//            Row(
//                modifier = Modifier.selectable(
//                    selected = person.isChild,
//                    onClick = { send(PersonViewModel.UIEvent.IsChildChanged(true)) }
//                ).padding(horizontal = 8.dp)
//            ) {
//                RadioButton(
//                    selected = person.isChild,
//                    onClick = { send(PersonViewModel.UIEvent.IsChildChanged(true)) }
//                )
//                Text(text = "Child")
//            }
//            Row(
//                modifier = Modifier.selectable(
//                    selected = !person.isChild,
//                    onClick = { send(PersonViewModel.UIEvent.IsChildChanged(false)) }
//                ).padding(horizontal = 8.dp)
//            ) {
//                RadioButton(
//                    selected = !person.isChild,
//                    onClick = { send(PersonViewModel.UIEvent.IsChildChanged(false)) }
//                )
//                Text(text = "Supervisor")
//            }
//        }
//
//        // Specific fields for Child or Supervisor
//        if (person.isChild) {
//            OutlinedTextFieldWrapper(
//                field = person.parentPhone!!,
//                onValueChange = { send(PersonViewModel.UIEvent.ParentPhoneChanged(it)) },
//                labelId = R.string.parent_phone
//            )
//        } else {
//            OutlinedTextFieldWrapper(
//                field = person.phone!!,
//                onValueChange = { send(PersonViewModel.UIEvent.PhoneChanged(it)) },
//                labelId = R.string.phone
//            )
//            OutlinedSpecialtiesFieldWrapper(
//                field = person.specialties!!,
//                onValueChange = { send(PersonViewModel.UIEvent.SpecialtiesChanged(it)) },
//                labelId = R.string.specialty
//            )
//            OutlinedTextFieldWrapper(
//                field = person.availability!!,
//                onValueChange = { send(PersonViewModel.UIEvent.AvailabilityChanged(it)) },
//                labelId = R.string.availability
//            )
//        }
//    }
//}
